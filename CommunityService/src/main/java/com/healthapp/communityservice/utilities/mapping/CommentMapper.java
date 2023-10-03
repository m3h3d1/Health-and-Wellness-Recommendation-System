package com.healthapp.communityservice.utilities.mapping;

import com.healthapp.communityservice.entities.Comment;
import com.healthapp.communityservice.entities.Post;
import com.healthapp.communityservice.models.commentdto.CommentCreateDTO;
import com.healthapp.communityservice.models.commentdto.CommentReadDTO;
import com.healthapp.communityservice.repositories.CommentRepository;
import com.healthapp.communityservice.repositories.PostRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CommentMapper {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public CommentMapper(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    public CommentReadDTO getCommentRead(Comment comment) {
        CommentReadDTO commentReadDTO = new CommentReadDTO();
        commentReadDTO.setCommentId(comment.getCommentId());
        commentReadDTO.setContent(comment.getContent());
        commentReadDTO.setTimeCreated(comment.getTimeCreated());
        commentReadDTO.setLikes(comment.getLikes() != null ? comment.getLikes().size() : 0);
        commentReadDTO.setDislikes(comment.getDislikes() != null ? comment.getDislikes().size() : 0);

        // Set the username
        commentReadDTO.setUserFullName(comment.getUserFullName());

        // Mapping replies recursively
        List<CommentReadDTO> replyDTOs = new ArrayList<>();
        for (Comment reply : comment.getReplies()) {
            replyDTOs.add(getCommentRead(reply));
        }
        commentReadDTO.setReplies(replyDTOs);
        return commentReadDTO;
    }

    public Comment getComment(CommentCreateDTO commentDTO){
        Comment comment = new Comment();
        Optional<Post> postOp = postRepository.findById(commentDTO.getParentPostId());
        if(postOp.isEmpty()){
            // Throw exception.
        }
        comment.setParentPost(postOp.get());
        if(commentDTO.getIsReply()){
            Optional<Comment> commentOp = commentRepository.findById(commentDTO.getParentCommentId());
            if(commentOp.isEmpty()){
                // Throw exception.
            }
            comment.setParentComment(commentOp.get());
        }
        comment.setDislikes(new ArrayList<>());
        comment.setLikes(new ArrayList<>());
        comment.setContent(commentDTO.getContent());
        comment.setTimeCreated(LocalDateTime.now());
        comment.setUserId(commentDTO.getUserId());
        comment.setReplies(new ArrayList<>());

        // Set the name of user through internal API call.
        comment.setUserFullName("Name Unavailable");
        // Write API call logics.

        return comment;
    }
}

