package com.healthapp.communityservice.controllers;

import com.healthapp.communityservice.models.commentdto.CommentCreateDTO;
import com.healthapp.communityservice.models.commentdto.CommentUpdateDTO;
import com.healthapp.communityservice.services.interfaces.CommentService;
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

    @PostMapping
    public ResponseEntity<String> createComment(@RequestBody CommentCreateDTO commentCreateDTO) {
        commentService.create(commentCreateDTO);
        return new ResponseEntity<>("Comment created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/by/post/{postId}")
    public ResponseEntity<?> getAllCommentsByPost(@PathVariable UUID postId){
        return ResponseEntity.ok(commentService.getCommentsByPostId(postId));
    }

    @GetMapping
    public ResponseEntity<?> getAllComments(){
        return ResponseEntity.ok(commentService.readAll());
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<?> getCommentById(@PathVariable UUID commentId){
        return ResponseEntity.ok(commentService.read(commentId));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<String> updateComment(@PathVariable UUID commentId, @RequestBody CommentUpdateDTO commentUpdateDTO) {
        commentService.update(commentId, commentUpdateDTO);
        return new ResponseEntity<>("Comment updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable UUID commentId) {
        commentService.delete(commentId);
        return new ResponseEntity<>("Comment deleted successfully", HttpStatus.NO_CONTENT);
    }

    @PostMapping("/interactions/{commentId}/add-like/{userId}")
    public ResponseEntity<String> addLike(@PathVariable UUID commentId, @PathVariable UUID userId){
        commentService.addLike(commentId, userId);
        return new ResponseEntity<>("Comment liked.", HttpStatus.OK);
    }

    @PostMapping("/interactions/{commentId}/add-dislike/{userId}")
    public ResponseEntity<String> addDislike(@PathVariable UUID commentId, @PathVariable UUID userId){
        commentService.addDislike(commentId, userId);
        return new ResponseEntity<>("Comment disliked.", HttpStatus.OK);
    }

    @DeleteMapping("/interactions/{commentId}/remove-interaction/{userId}")
    public ResponseEntity<String> removeInteraction(@PathVariable UUID commentId, @PathVariable UUID userId){
        commentService.removeInteraction(commentId, userId);
        return new ResponseEntity<>("Like/Dislike removed.", HttpStatus.OK);
    }
}
