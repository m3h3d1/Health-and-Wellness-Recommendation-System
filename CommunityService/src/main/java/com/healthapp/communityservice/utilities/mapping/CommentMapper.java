package com.healthapp.communityservice.utilities.mapping;

import com.healthapp.communityservice.entities.Comment;
import com.healthapp.communityservice.entities.Post;
import com.healthapp.communityservice.exceptions.NotFoundException;
import com.healthapp.communityservice.exceptions.PostNotFoundException;
import com.healthapp.communityservice.models.commentdto.CommentCreateDTO;
import com.healthapp.communityservice.models.commentdto.CommentReadDTO;
import com.healthapp.communityservice.networks.UserDTO;
import com.healthapp.communityservice.networks.UserServiceProxy;
import com.healthapp.communityservice.repositories.CommentRepository;
import com.healthapp.communityservice.repositories.PostRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class CommentMapper {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserServiceProxy userServiceProxy;

    public CommentMapper(PostRepository postRepository, CommentRepository commentRepository, UserServiceProxy userServiceProxy) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.userServiceProxy = userServiceProxy;
    }

    /**
     * Map a Comment entity to a CommentReadDTO.
     *
     * @param comment The Comment entity to map.
     * @return A CommentReadDTO representing the mapped Comment entity.
     */
    public CommentReadDTO getCommentRead(Comment comment) {
        CommentReadDTO commentReadDTO = new CommentReadDTO();
        commentReadDTO.setCommentId(comment.getCommentId());
        commentReadDTO.setContent(comment.getContent());
        commentReadDTO.setTimeCreated(comment.getTimeCreated());
        commentReadDTO.setLikes(comment.getLikes() != null ? comment.getLikes().size() : 0);
        commentReadDTO.setDislikes(comment.getDislikes() != null ? comment.getDislikes().size() : 0);

        // Set the username
        commentReadDTO.setUserFullName(comment.getUserFullName());
        if (comment.getUserFullName() == null || comment.getUserFullName().equals("Name Unavailable")) {
            try {
                ResponseEntity<UserDTO> userResponse = userServiceProxy.getUser(comment.getUserId());
                String firstName = Objects.requireNonNull(userResponse.getBody()).getFirstName();
                String lastName = Objects.requireNonNull(userResponse.getBody()).getLastName();
                commentReadDTO.setUserFullName(firstName + " " + lastName);
            } catch (Exception e) {
                comment.setUserFullName("Name Unavailable");
            }
        }

        // Mapping replies recursively
        List<CommentReadDTO> replyDTOs = new ArrayList<>();
        for (Comment reply : comment.getReplies()) {
            replyDTOs.add(getCommentRead(reply));
        }
        commentReadDTO.setReplies(replyDTOs);
        return commentReadDTO;
    }

    /**
     * Map a CommentCreateDTO to a Comment entity.
     *
     * @param commentDTO The CommentCreateDTO to map.
     * @return A Comment entity representing the mapped CommentCreateDTO.
     */
    public Comment getComment(CommentCreateDTO commentDTO) {
        Comment comment = new Comment();
        Optional<Post> postOp = postRepository.findById(commentDTO.getParentPostId());
        if (postOp.isEmpty()) {
            throw new PostNotFoundException("The post with post id " + commentDTO.getParentPostId() + " doesn't exist.");
        }
        comment.setParentPost(postOp.get());
        if (commentDTO.getIsReply()) {
            Optional<Comment> commentOp = commentRepository.findById(commentDTO.getParentCommentId());
            if (commentOp.isEmpty()) {
                throw new NotFoundException("CommentNotFoundException", "Fetching the parent comment comment",
                        "Comment with id " + commentDTO.getParentCommentId() + " does not exist.");
            }
            comment.setParentComment(commentOp.get());
        }
        comment.setDislikes(new ArrayList<>());
        comment.setLikes(new ArrayList<>());
        comment.setContent(commentDTO.getContent());
        comment.setTimeCreated(LocalDateTime.now());
        comment.setUserId(commentDTO.getUserId());
        comment.setReplies(new ArrayList<>());

        // Setting the name of the user through an internal API call.
        try {
            ResponseEntity<UserDTO> userResponse = userServiceProxy.getUser(commentDTO.getUserId());
            String firstName = Objects.requireNonNull(userResponse.getBody()).getFirstName();
            String lastName = Objects.requireNonNull(userResponse.getBody()).getLastName();
            comment.setUserFullName(firstName + " " + lastName);
        } catch (Exception e) {
            comment.setUserFullName("Name Unavailable");
        }

        return comment;
    }
}
