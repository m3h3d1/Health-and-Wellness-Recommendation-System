package com.healthapp.communityservice.controllers;

import com.healthapp.communityservice.models.commentdto.CommentCreateDTO;
import com.healthapp.communityservice.models.commentdto.CommentUpdateDTO;
import com.healthapp.communityservice.services.interfaces.CommentService;
import com.healthapp.communityservice.utilities.token.IDExtractor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/community/posts/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // Create a new comment
    @PostMapping
    public ResponseEntity<String> createComment(@RequestBody CommentCreateDTO commentCreateDTO) {
        commentService.create(commentCreateDTO);
        return new ResponseEntity<>("Comment created successfully", HttpStatus.CREATED);
    }

    // Get all comments for a specific post by post ID
    @GetMapping("/by/post/{postId}")
    public ResponseEntity<?> getAllCommentsByPost(@PathVariable UUID postId){
        return ResponseEntity.ok(commentService.getCommentsByPostId(postId));
    }

    // Get a list of all comments
    @GetMapping
    public ResponseEntity<?> getAllComments(){
        return ResponseEntity.ok(commentService.readAll());
    }

    // Get a comment by its ID
    @GetMapping("/{commentId}")
    public ResponseEntity<?> getCommentById(@PathVariable UUID commentId){
        return ResponseEntity.ok(commentService.read(commentId));
    }

    // Update an existing comment by its ID
    @PutMapping("/{commentId}")
    public ResponseEntity<String> updateComment(@PathVariable UUID commentId, @RequestBody CommentUpdateDTO commentUpdateDTO) {
        commentService.update(commentId, commentUpdateDTO);
        return new ResponseEntity<>("Comment updated successfully", HttpStatus.OK);
    }

    // Delete a comment by its ID
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable UUID commentId) {
        commentService.delete(commentId);
        return new ResponseEntity<>("Comment deleted successfully", HttpStatus.NO_CONTENT);
    }

    // Add a "like" interaction to a comment
    @PostMapping("/interactions/{commentId}/add-like")
    public ResponseEntity<String> addLike(@PathVariable UUID commentId){
        commentService.addLike(commentId, IDExtractor.getUserID());
        return new ResponseEntity<>("Comment liked.", HttpStatus.OK);
    }

    // Add a "dislike" interaction to a comment
    @PostMapping("/interactions/{commentId}/add-dislike")
    public ResponseEntity<String> addDislike(@PathVariable UUID commentId){
        commentService.addDislike(commentId, IDExtractor.getUserID());
        return new ResponseEntity<>("Comment disliked.", HttpStatus.OK);
    }

    // Remove a like or dislike interaction from a comment
    @DeleteMapping("/interactions/{commentId}/remove-interaction")
    public ResponseEntity<String> removeInteraction(@PathVariable UUID commentId){
        commentService.removeInteraction(commentId, IDExtractor.getUserID());
        return new ResponseEntity<>("Like/Dislike removed.", HttpStatus.OK);
    }
}
