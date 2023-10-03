package com.healthapp.communityservice.services.implementations;

import com.healthapp.communityservice.entities.Connection;
import com.healthapp.communityservice.entities.Following;
import com.healthapp.communityservice.entities.Post;
import com.healthapp.communityservice.exceptions.InvalidUnfollowException;
import com.healthapp.communityservice.exceptions.MultipleFollowRequestException;
import com.healthapp.communityservice.models.postdto.PostReadDTO;
import com.healthapp.communityservice.repositories.ConnectionRepository;
import com.healthapp.communityservice.services.interfaces.ConnectionService;
import com.healthapp.communityservice.services.interfaces.PostService;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class ConnectionServiceImpl implements ConnectionService {

    private final ConnectionRepository connectionRepository;
    private final PostService postService;

    public ConnectionServiceImpl(ConnectionRepository connectionRepository, PostService postService) {
        this.connectionRepository = connectionRepository;
        this.postService = postService;
    }

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
    }

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

    @Override
    public List<Following> following(UUID followerId) {
        List<Connection> connections = connectionRepository.findByFollowerId(followerId);
        if(connections.isEmpty()){
            return new ArrayList<>();
        }
        return connections.get(0).getFollowing();
    }

    @Override
    public List<PostReadDTO> followingFeed(UUID followerId) {
        List<PostReadDTO> feeds = new ArrayList<>();
        for(Following following: following(followerId)){
            feeds.addAll(postService.findByUser(following.getFollowedUserId()));
        }
        return feeds;
    }
}
