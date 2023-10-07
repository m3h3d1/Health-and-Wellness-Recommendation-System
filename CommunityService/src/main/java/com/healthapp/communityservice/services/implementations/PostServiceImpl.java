package com.healthapp.communityservice.services.implementations;

import com.healthapp.communityservice.entities.Interact;
import com.healthapp.communityservice.entities.Post;
import com.healthapp.communityservice.enums.PostPrivacy;
import com.healthapp.communityservice.exceptions.InteractionBlockedException;
import com.healthapp.communityservice.exceptions.PostNotFoundException;
import com.healthapp.communityservice.exceptions.PostPrivacyException;
import com.healthapp.communityservice.models.postdto.PostCreateDTO;
import com.healthapp.communityservice.models.postdto.PostReadDTO;
import com.healthapp.communityservice.models.postdto.PostUpdateDTO;
import com.healthapp.communityservice.networks.NotificationDTO;
import com.healthapp.communityservice.networks.NotificationServiceProxy;
import com.healthapp.communityservice.repositories.PostRepository;
import com.healthapp.communityservice.services.interfaces.PostService;
import com.healthapp.communityservice.utilities.constants.TokenConstants;
import com.healthapp.communityservice.utilities.mapping.PostMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final NotificationServiceProxy notificationServiceProxy;

    public PostServiceImpl(PostRepository postRepository, PostMapper postMapper, NotificationServiceProxy notificationServiceProxy) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.notificationServiceProxy = notificationServiceProxy;
    }

    /**
     * Create a new post.
     *
     * @param postCreateDTO The DTO containing post creation data.
     */
    @Override
    public void create(PostCreateDTO postCreateDTO) {
        Post post = postMapper.getPost(postCreateDTO);
        postRepository.save(post);
    }

    /**
     * Retrieve a post by its UUID.
     *
     * @param postUUID The UUID of the post to retrieve.
     * @return A DTO representing the post.
     * @throws PostNotFoundException If the post does not exist.
     */
    @Override
    public PostReadDTO read(UUID postUUID) {
        Optional<Post> postOp = postRepository.findById(postUUID);
        if(postOp.isEmpty()){
            throw new PostNotFoundException("The post with post id " + postUUID + " doesn't exist.");
        }
        return postMapper.getPostRead(postOp.get());
    }

    /**
     * Get a list of all posts.
     *
     * @return A list of DTOs representing all posts.
     */
    @Override
    public List<PostReadDTO> readAll() {
        return postRepository.findAll().stream().map(postMapper::getPostRead).collect(Collectors.toList());
    }

    /**
     * Get a list of posts by a user's UUID.
     *
     * @param userId The UUID of the user.
     * @return A list of DTOs representing the user's posts.
     */
    @Override
    public List<PostReadDTO> findByUser(UUID userId) {
        return postRepository.findByUserId(userId).stream().map(postMapper::getPostRead).collect(Collectors.toList());
    }

    /**
     * Update a post's content and privacy settings.
     *
     * @param postId        The UUID of the post to update.
     * @param postUpdateDTO The DTO containing updated post data.
     * @throws PostPrivacyException If the post's privacy cannot be updated.
     */
    @Override
    public void update(UUID postId, UUID requestUserId, PostUpdateDTO postUpdateDTO) {
        Optional<Post> postOp = postRepository.findById(postId);
        if(postOp.isEmpty()){
            throw new PostNotFoundException("The post with post id " + postId + " doesn't exist.");
        }
        Post post = postOp.get();
        if(!post.getUserId().equals(requestUserId)){
            throw new InteractionBlockedException("Requested user doesn't own the post.");
        }
        post.setContent(postUpdateDTO.getContent());
        if(post.getPrivacy().equals(PostPrivacy.GROUP) && postUpdateDTO.getPrivacy() != PostPrivacy.GROUP){
            throw new PostPrivacyException("Privacy of group post is public by default, and cannot be updated.");
        }
        post.setPrivacy(postUpdateDTO.getPrivacy());
        postRepository.save(post);
    }

    /**
     * Delete a post by its UUID.
     *
     * @param postId The UUID of the post to delete.
     * @throws PostNotFoundException If the post does not exist.
     */
    @Override
    public void delete(UUID postId, UUID requestUserId) {
        Optional<Post> postOp = postRepository.findById(postId);
        if(postOp.isEmpty()){
            throw new PostNotFoundException("The post with post id " + postId + " doesn't exist.");
        }
        Post post = postOp.get();
        if(!post.getUserId().equals(requestUserId)){
            throw new InteractionBlockedException("Requested user doesn't own the post.");
        }
        postRepository.deleteById(postId);
    }

    /**
     * Add a like to a post by a user.
     *
     * @param postId The UUID of the post to like.
     * @param userId The UUID of the user who likes the post.
     * @throws InteractionBlockedException If the user has already liked the post.
     */
    @Override
    public void addLike(UUID postId, UUID userId) {
        Optional<Post> postOp = postRepository.findById(postId);
        if(postOp.isEmpty()){
            throw new PostNotFoundException("There is no post with given id " + postId);
        }
        Post post = postOp.get();
        if(post.getLikes().stream().filter(p -> p.getUserId().equals(userId)).collect(Collectors.toList()).isEmpty()){
            Interact interact = new Interact();
            interact.setUserId(userId);
            post.getLikes().add(interact);
            post.setDislikes(post.getDislikes().stream().filter(p -> !p.getUserId().equals(userId)).collect(Collectors.toList()));
        }
        else{
            throw new InteractionBlockedException("Post is already liked by the user.");
        }
        postRepository.save(post);

        // Send notification
        NotificationDTO notification = new NotificationDTO();
        notification.setText("Your post \"" + (post.getContent().length() < 8 ? post.getContent() : post.getContent().substring(0, 8)) + "...\" has got a new like.");
        notificationServiceProxy.send(post.getUserId(), TokenConstants.TOKEN_SECRET, notification);
    }

    /**
     * Add a dislike to a post by a user.
     *
     * @param postId The UUID of the post to dislike.
     * @param userId The UUID of the user who dislikes the post.
     * @throws InteractionBlockedException If the user has already disliked the post.
     */
    @Override
    public void addDislike(UUID postId, UUID userId) {
        Optional<Post> postOp = postRepository.findById(postId);
        if(postOp.isEmpty()){
            throw new PostNotFoundException("There is no post with given id " + postId);
        }
        Post post = postOp.get();
        if(post.getDislikes().stream().filter(p -> p.getUserId().equals(userId)).collect(Collectors.toList()).isEmpty()){
            Interact interact = new Interact();
            interact.setUserId(userId);
            post.getDislikes().add(interact);
            post.setLikes(post.getLikes().stream().filter(p -> !p.getUserId().equals(userId)).collect(Collectors.toList()));
        }
        else{
            throw new InteractionBlockedException("Post is already disliked by the user.");
        }
        postRepository.save(post);
    }

    /**
     * Add a user as a follower of a post.
     *
     * @param postId The UUID of the post to follow.
     * @param userId The UUID of the user who wants to follow the post.
     * @throws InteractionBlockedException If the user is already following the post.
     */
    @Override
    public void addFollower(UUID postId, UUID userId) {
        Optional<Post> postOp = postRepository.findById(postId);
        if(postOp.isEmpty()){
            throw new PostNotFoundException("There is no post with given id " + postId);
        }
        Post post = postOp.get();
        if(post.getFollowers().stream().filter(p -> p.getUserId().equals(userId)).collect(Collectors.toList()).isEmpty()){
            Interact interact = new Interact();
            interact.setUserId(userId);
            post.getFollowers().add(interact);
        }
        else{
            throw new InteractionBlockedException("Post is already followed by the user.");
        }
        postRepository.save(post);

        // Send notification
        NotificationDTO notification = new NotificationDTO();
        notification.setText("Your post \"" + (post.getContent().length() < 8 ? post.getContent() : post.getContent().substring(0, 8)) + "...\" has got a new follower.");
        notificationServiceProxy.send(post.getUserId(), TokenConstants.TOKEN_SECRET, notification);
    }

    /**
     * Remove a user as a follower of a post.
     *
     * @param postId The UUID of the post to unfollow.
     * @param userId The UUID of the user who wants to unfollow the post.
     * @throws InteractionBlockedException If the user is not following the post.
     */
    @Override
    public void removeFollower(UUID postId, UUID userId){
        Optional<Post> postOp = postRepository.findById(postId);
        if(postOp.isEmpty()){
            throw new PostNotFoundException("There is no post with given id " + postId);
        }
        Post post = postOp.get();
        if(!post.getFollowers().stream().filter(p -> p.getUserId().equals(userId)).collect(Collectors.toList()).isEmpty()){
            post.setFollowers(post.getFollowers().stream().filter(p -> !p.getUserId().equals(userId)).collect(Collectors.toList()));
        }
        else{
            throw new InteractionBlockedException("The user is not following the post.");
        }
        postRepository.save(post);
    }

    /**
     * Remove a user's interaction (like/dislike) from a post.
     *
     * @param postId The UUID of the post.
     * @param userId The UUID of the user whose interaction to remove.
     */
    @Override
    public void removeInteraction(UUID postId, UUID userId) {
        Optional<Post> postOp = postRepository.findById(postId);
        if(postOp.isEmpty()){
            throw new PostNotFoundException("There is no post with given id " + postId);
        }
        Post post = postOp.get();
        post.setLikes(post.getLikes().stream().filter(p -> !p.getUserId().equals(userId)).collect(Collectors.toList()));
        post.setDislikes(post.getDislikes().stream().filter(p -> !p.getUserId().equals(userId)).collect(Collectors.toList()));
        postRepository.save(post);
    }
}
