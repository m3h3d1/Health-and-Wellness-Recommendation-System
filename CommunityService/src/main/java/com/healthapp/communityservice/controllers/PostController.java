package com.healthapp.communityservice.controllers;

import com.healthapp.communityservice.models.postdto.PostCreateDTO;
import com.healthapp.communityservice.models.postdto.PostReadDTO;
import com.healthapp.communityservice.models.postdto.PostUpdateDTO;
import com.healthapp.communityservice.services.interfaces.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/community/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostCreateDTO postCreateDTO) {
        postService.create(postCreateDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostReadDTO> getPost(@PathVariable UUID postId) {
        PostReadDTO post = postService.read(postId);
        if (post != null) {
            return new ResponseEntity<>(post, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<PostReadDTO>> getAllPosts() {
        List<PostReadDTO> posts = postService.readAll();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Void> updatePost(@PathVariable UUID postId, @RequestBody PostUpdateDTO postUpdateDTO) {
        postService.update(postId, postUpdateDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable UUID postId) {
        postService.delete(postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/interactions/{postId}/add-like/{userId}")
    public ResponseEntity<Void> addLike(@PathVariable UUID postId, @PathVariable UUID userId) {
        postService.addLike(postId, userId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/interactions/{postId}/add-dislike/{userId}")
    public ResponseEntity<Void> addDislike(@PathVariable UUID postId, @PathVariable UUID userId) {
        postService.addDislike(postId, userId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/interactions/{postId}/add-follower/{userId}")
    public ResponseEntity<Void> addFollower(@PathVariable UUID postId, @PathVariable UUID userId) {
        postService.addFollower(postId, userId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/interactions/{postId}/remove-follower/{userId}")
    public ResponseEntity<Void> removeFollower(@PathVariable UUID postId, @PathVariable UUID userId) {
        postService.removeFollower(postId, userId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/interactions/{postId}/remove-interaction/{userId}")
    public ResponseEntity<String> removeInteraction(@PathVariable UUID postId, @PathVariable UUID userId){
        postService.removeInteraction(postId, userId);
        return new ResponseEntity<>("Like/Dislike removed.", HttpStatus.OK);
    }
}


