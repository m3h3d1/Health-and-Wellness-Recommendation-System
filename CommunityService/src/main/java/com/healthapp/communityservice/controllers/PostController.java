package com.healthapp.communityservice.controllers;

import com.healthapp.communityservice.models.postdto.PostCreateDTO;
import com.healthapp.communityservice.models.postdto.PostReadDTO;
import com.healthapp.communityservice.models.postdto.PostUpdateDTO;
import com.healthapp.communityservice.services.interfaces.PostService;
import com.healthapp.communityservice.utilities.token.IDExtractor;
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

    // Create a new post by providing post details in the request body
    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostCreateDTO postCreateDTO) {
        postCreateDTO.setUserId(IDExtractor.getUserID());
        postService.create(postCreateDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // Get information about a post by specifying its unique ID
    @GetMapping("/{postId}")
    public ResponseEntity<PostReadDTO> getPost(@PathVariable UUID postId) {
        PostReadDTO post = postService.read(postId);
        if (post != null) {
            return new ResponseEntity<>(post, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get a list of all posts
    @GetMapping
    public ResponseEntity<List<PostReadDTO>> getAllPosts() {
        List<PostReadDTO> posts = postService.readAll();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    // Get a list of all posts by a specific user
    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<PostReadDTO>> getAllPostsByUser(@PathVariable UUID userId) {
        List<PostReadDTO> posts = postService.findByUser(userId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    // Update post information by specifying the post's ID and providing updated details in the request body
    @PutMapping("/{postId}")
    public ResponseEntity<String> updatePost(@PathVariable UUID postId, @RequestBody PostUpdateDTO postUpdateDTO) {
        postService.update(postId, IDExtractor.getUserID() ,postUpdateDTO);
        return ResponseEntity.ok("Post updated successfully");
    }

    // Delete a post by specifying its unique ID
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable UUID postId) {
        postService.delete(postId, IDExtractor.getUserID());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Add a like to a post by specifying the post ID and user ID
    @PostMapping("/interactions/{postId}/add-like")
    public ResponseEntity<Void> addLike(@PathVariable UUID postId) {
        postService.addLike(postId, IDExtractor.getUserID());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // Add a dislike to a post by specifying the post ID and user ID
    @PostMapping("/interactions/{postId}/add-dislike")
    public ResponseEntity<Void> addDislike(@PathVariable UUID postId) {
        postService.addDislike(postId, IDExtractor.getUserID());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // Add a follower to a post by specifying the post ID and user ID
    @PostMapping("/interactions/{postId}/add-follower")
    public ResponseEntity<Void> addFollower(@PathVariable UUID postId) {
        postService.addFollower(postId, IDExtractor.getUserID());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // Remove a follower from a post by specifying the post ID and user ID
    @PostMapping("/interactions/{postId}/remove-follower")
    public ResponseEntity<Void> removeFollower(@PathVariable UUID postId) {
        postService.removeFollower(postId, IDExtractor.getUserID());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // Remove a like/dislike from a post by specifying the post ID and user ID
    @DeleteMapping("/interactions/{postId}/remove-interaction")
    public ResponseEntity<String> removeInteraction(@PathVariable UUID postId){
        postService.removeInteraction(postId, IDExtractor.getUserID());
        return new ResponseEntity<>("Interactions removed.", HttpStatus.OK);
    }
}
