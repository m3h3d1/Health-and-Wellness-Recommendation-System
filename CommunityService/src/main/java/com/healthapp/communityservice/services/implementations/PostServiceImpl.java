package com.healthapp.communityservice.services.implementations;

import com.healthapp.communityservice.entities.Interact;
import com.healthapp.communityservice.entities.Post;
import com.healthapp.communityservice.enums.PostPrivacy;
import com.healthapp.communityservice.models.postdto.PostCreateDTO;
import com.healthapp.communityservice.models.postdto.PostReadDTO;
import com.healthapp.communityservice.models.postdto.PostUpdateDTO;
import com.healthapp.communityservice.repositories.PostRepository;
import com.healthapp.communityservice.services.interfaces.PostService;
import com.healthapp.communityservice.utilities.mapping.PostMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public PostServiceImpl(PostRepository postRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    @Override
    public void create(PostCreateDTO postCreateDTO) {
        Post post = postMapper.getPost(postCreateDTO);
        postRepository.save(post);
    }

    @Override
    public PostReadDTO read(UUID postUUID) {
        Optional<Post> postOp = postRepository.findById(postUUID);
        if(postOp.isEmpty()){
            return null;
        }
        return postMapper.getPostRead(postOp.get());
    }

    @Override
    public List<PostReadDTO> readAll() {
        return postRepository.findAll().stream().map(postMapper::getPostRead).collect(Collectors.toList());
    }

    @Override
    public void update(UUID postId, PostUpdateDTO postUpdateDTO) {
        Optional<Post> postOp = postRepository.findById(postId);
        if(postOp.isEmpty()){
            return;
        }
        Post post = postOp.get();
        post.setContent(postUpdateDTO.getContent());
        if(post.getPrivacy().equals(PostPrivacy.GROUP) && postUpdateDTO.getPrivacy() != PostPrivacy.GROUP){
            // Throw group post exception.
        }
        post.setPrivacy(postUpdateDTO.getPrivacy());
        postRepository.save(post);
    }

    @Override
    public void delete(UUID postId) {
        read(postId);
        postRepository.deleteById(postId);
    }

    @Override
    public void addLike(UUID postId, UUID userId) {
        Optional<Post> postOp = postRepository.findById(postId);
        if(postOp.isEmpty()){
            return;
        }
        Post post = postOp.get();
        if(post.getLikes().stream().filter(p -> p.getUserId().equals(userId)).collect(Collectors.toList()).isEmpty()){
            Interact interact = new Interact();
            interact.setUserId(userId);
            post.getLikes().add(interact);
            post.setDislikes(post.getDislikes().stream().filter(p -> !p.getUserId().equals(userId)).collect(Collectors.toList()));
        }
        postRepository.save(post);
    }

    @Override
    public void addDislike(UUID postId, UUID userId) {
        Optional<Post> postOp = postRepository.findById(postId);
        if(postOp.isEmpty()){
            return;
        }
        Post post = postOp.get();
        if(post.getDislikes().stream().filter(p -> p.getUserId().equals(userId)).collect(Collectors.toList()).isEmpty()){
            Interact interact = new Interact();
            interact.setUserId(userId);
            post.getDislikes().add(interact);
            post.setLikes(post.getLikes().stream().filter(p -> !p.getUserId().equals(userId)).collect(Collectors.toList()));
        }
        postRepository.save(post);
    }

    @Override
    public void addFollower(UUID postId, UUID userId) {
        Optional<Post> postOp = postRepository.findById(postId);
        if(postOp.isEmpty()){
            return;
        }
        Post post = postOp.get();
        if(post.getFollowers().stream().filter(p -> p.getUserId().equals(userId)).collect(Collectors.toList()).isEmpty()){
            Interact interact = new Interact();
            interact.setUserId(userId);
            post.getFollowers().add(interact);
        }
        postRepository.save(post);
    }
    @Override
    public void removeFollower(UUID postId, UUID userId){
        Optional<Post> postOp = postRepository.findById(postId);
        if(postOp.isEmpty()){
            return;
        }
        Post post = postOp.get();
        if(!post.getFollowers().stream().filter(p -> p.getUserId().equals(userId)).collect(Collectors.toList()).isEmpty()){
            post.setFollowers(post.getFollowers().stream().filter(p -> !p.getUserId().equals(userId)).collect(Collectors.toList()));
        }
        postRepository.save(post);
    }

    @Override
    public void removeInteraction(UUID postId, UUID userId) {
        Optional<Post> postOp = postRepository.findById(postId);
        if(postOp.isEmpty()){
            return;
        }
        Post post = postOp.get();
        post.setLikes(post.getLikes().stream().filter(p -> !p.getUserId().equals(userId)).collect(Collectors.toList()));
        post.setDislikes(post.getDislikes().stream().filter(p -> !p.getUserId().equals(userId)).collect(Collectors.toList()));
        postRepository.save(post);
    }
}
