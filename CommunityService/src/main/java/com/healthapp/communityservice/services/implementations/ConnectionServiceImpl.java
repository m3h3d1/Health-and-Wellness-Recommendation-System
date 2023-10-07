package com.healthapp.communityservice.services.implementations;

import com.healthapp.communityservice.entities.Connection;
import com.healthapp.communityservice.entities.Following;
import com.healthapp.communityservice.exceptions.InvalidUnfollowException;
import com.healthapp.communityservice.exceptions.MultipleFollowRequestException;
import com.healthapp.communityservice.models.postdto.PostReadDTO;
import com.healthapp.communityservice.networks.NotificationDTO;
import com.healthapp.communityservice.networks.NotificationServiceProxy;
import com.healthapp.communityservice.repositories.ConnectionRepository;
import com.healthapp.communityservice.services.interfaces.ConnectionService;
import com.healthapp.communityservice.services.interfaces.PostService;
import com.healthapp.communityservice.utilities.constants.TokenConstants;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class ConnectionServiceImpl implements ConnectionService {

    private final ConnectionRepository connectionRepository;
    private final PostService postService;
    private final NotificationServiceProxy notificationServiceProxy;

    public ConnectionServiceImpl(ConnectionRepository connectionRepository, PostService postService, NotificationServiceProxy notificationServiceProxy) {
        this.connectionRepository = connectionRepository;
        this.postService = postService;
        this.notificationServiceProxy = notificationServiceProxy;
    }

    /**
     * Follow a user.
     *
     * @param followerId   The UUID of the follower.
     * @param followingId  The UUID of the user to follow.
     * @throws MultipleFollowRequestException If the user is already being followed by the follower.
     */
    @Override
    public void follow(UUID followerId, UUID followingId) {
        List<Connection> connections = connectionRepository.findAll()
                .stream().filter(f -> f.getFollowerId().equals(followerId)).toList();

        Connection connection;
        if(connections.isEmpty()){
            connection = new Connection();
            connection.setFollowerId(followerId);
            connection.setFollowing(new ArrayList<>());
        }
        else {
            connection = connections.get(0);
        }
        for(Following following: connection.getFollowing()){
            if(following.getFollowedUserId().equals(followingId)){
                throw new MultipleFollowRequestException("The requested user already follows the user with ID " + following.getFollowedUserId());
            }
        }
        Following newFollow = new Following();
        newFollow.setFollowedUserId(followingId);
        newFollow.setFollowTime(LocalDateTime.now());

        connection.getFollowing().add(newFollow);
        connectionRepository.save(connection);

        // Send notification
        NotificationDTO notification = new NotificationDTO();
        notification.setText("You have a new follower.");
        notificationServiceProxy.send(followingId, TokenConstants.TOKEN_SECRET, notification);
    }

    /**
     * Unfollow a user.
     *
     * @param followerId   The UUID of the follower.
     * @param followingId  The UUID of the user to unfollow.
     * @throws InvalidUnfollowException If the user is not being followed by the follower or has no connections.
     */
    @Override
    public void unFollow(UUID followerId, UUID followingId) {
        List<Connection> connections = connectionRepository.findByFollowerId(followerId);

        if (!connections.isEmpty()) {
            Connection connection = connections.get(0);
            List<Following> followingList = connection.getFollowing();
            boolean removed = followingList.removeIf(following -> following.getFollowedUserId().equals(followingId));

            if (removed) {
                connectionRepository.save(connection);
            } else {
                throw new InvalidUnfollowException("Requested user is not following the given user.");
            }
        } else {
            throw new InvalidUnfollowException("User does not have any connections.");
        }
    }

    /**
     * Get the list of users followed by a follower.
     *
     * @param followerId The UUID of the follower.
     * @return A list of Following entities.
     */
    @Override
    public List<Following> following(UUID followerId) {
        List<Connection> connections = connectionRepository.findByFollowerId(followerId);
        if(connections.isEmpty()){
            return new ArrayList<>();
        }
        return connections.get(0).getFollowing();
    }

    /**
     * Get the feed of posts from users followed by a follower.
     *
     * @param followerId The UUID of the follower.
     * @return A list of PostReadDTO containing posts from followed users.
     */
    @Override
    public List<PostReadDTO> followingFeed(UUID followerId) {
        List<PostReadDTO> feeds = new ArrayList<>();
        for(Following following: following(followerId)){
            feeds.addAll(postService.findByUser(following.getFollowedUserId()));
        }
        return feeds;
    }
}
