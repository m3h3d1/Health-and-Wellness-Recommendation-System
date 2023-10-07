package com.healthapp.communityservice.services.implementations;

import com.healthapp.communityservice.entities.Comment;
import com.healthapp.communityservice.entities.Interact;
import com.healthapp.communityservice.exceptions.InteractionBlockedException;
import com.healthapp.communityservice.exceptions.NotFoundException;
import com.healthapp.communityservice.models.commentdto.CommentCreateDTO;
import com.healthapp.communityservice.models.commentdto.CommentReadDTO;
import com.healthapp.communityservice.models.commentdto.CommentUpdateDTO;
import com.healthapp.communityservice.networks.NotificationDTO;
import com.healthapp.communityservice.networks.NotificationServiceProxy;
import com.healthapp.communityservice.repositories.CommentRepository;
import com.healthapp.communityservice.services.interfaces.CommentService;
import com.healthapp.communityservice.utilities.constants.TokenConstants;
import com.healthapp.communityservice.utilities.mapping.CommentMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final NotificationServiceProxy notificationServiceProxy;

    public CommentServiceImpl(CommentRepository commentRepository, CommentMapper commentMapper, NotificationServiceProxy notificationServiceProxy) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.notificationServiceProxy = notificationServiceProxy;
    }

    /**
     * Create a new comment.
     *
     * @param commentCreateDTO The DTO containing comment creation data.
     */
    @Override
    public void create(CommentCreateDTO commentCreateDTO) {
        Comment comment = commentMapper.getComment(commentCreateDTO);
        commentRepository.save(comment);
    }

    /**
     * Read a comment by its ID.
     *
     * @param commentId The UUID of the comment to read.
     * @return The CommentReadDTO representing the comment.
     * @throws NotFoundException If the comment does not exist.
     */
    @Override
    public CommentReadDTO read(UUID commentId) {
        Optional<Comment> commentOp = commentRepository.findById(commentId);
        if (commentOp.isEmpty()) {
            throw new NotFoundException("CommentNotFoundException", "Fetching a comment", "Comment with id " + commentId + " does not exist.");
        }
        return commentMapper.getCommentRead(commentOp.get());
    }

    /**
     * Get comments associated with a specific post.
     *
     * @param postId The UUID of the post to retrieve comments for.
     * @return A list of CommentReadDTO representing comments on the post.
     */
    @Override
    public List<CommentReadDTO> getCommentsByPostId(UUID postId) {
        List<Comment> comments = commentRepository.findAll().stream()
                .filter(c -> c.getParentPost().getPostId().equals(postId))
                .toList();
        return comments.stream()
                .map(commentMapper::getCommentRead)
                .collect(Collectors.toList());
    }

    /**
     * Get all comments.
     *
     * @return A list of CommentReadDTO representing all comments.
     */
    @Override
    public List<CommentReadDTO> readAll() {
        return commentRepository.findAll().stream()
                .map(commentMapper::getCommentRead)
                .collect(Collectors.toList());
    }

    /**
     * Update a comment's content.
     *
     * @param commentId        The UUID of the comment to update.
     * @param commentUpdateDTO The DTO containing updated comment data.
     * @throws NotFoundException If the comment does not exist.
     */
    @Override
    public void update(UUID commentId, CommentUpdateDTO commentUpdateDTO) {
        Optional<Comment> commentOp = commentRepository.findById(commentId);
        if (commentOp.isEmpty()) {
            throw new NotFoundException("CommentNotFoundException", "Fetching a comment", "Comment with id " + commentId + " does not exist.");
        }
        Comment comment = commentOp.get();
        comment.setContent(commentUpdateDTO.getContent());
        commentRepository.save(comment);
    }

    /**
     * Delete a comment.
     *
     * @param commentId The UUID of the comment to delete.
     * @throws NotFoundException If the comment does not exist.
     */
    @Override
    public void delete(UUID commentId) {
        read(commentId);
        commentRepository.deleteById(commentId);
    }

    /**
     * Add a like to a comment.
     *
     * @param commentId The UUID of the comment to like.
     * @param userId    The UUID of the user liking the comment.
     * @throws NotFoundException        If the comment does not exist.
     * @throws InteractionBlockedException If the comment is already liked by the user.
     */
    @Override
    public void addLike(UUID commentId, UUID userId) {
        Optional<Comment> commentOp = commentRepository.findById(commentId);
        if (commentOp.isEmpty()) {
            throw new NotFoundException("CommentNotFoundException", "Fetching a comment", "Comment with id " + commentId + " does not exist.");
        }
        Comment comment = commentOp.get();
        if(comment.getLikes().stream().filter(p -> p.getUserId().equals(userId)).collect(Collectors.toList()).isEmpty()){
            Interact interact = new Interact();
            interact.setUserId(userId);
            comment.getLikes().add(interact);
            comment.setDislikes(comment.getLikes().stream().filter(p -> !p.getUserId().equals(userId)).collect(Collectors.toList()));
        }
        else{
            throw new InteractionBlockedException("Comment is already liked by the user.");
        }
        commentRepository.save(comment);
        // Send notification
        NotificationDTO notification = new NotificationDTO();
        notification.setText("Your comment \"" + (comment.getContent().length() < 8 ? comment.getContent() : comment.getContent().substring(0, 8)) + "...\" has got a new follower.");
        notificationServiceProxy.send(comment.getUserId(), TokenConstants.TOKEN_SECRET, notification);
    }

    /**
     * Add a dislike to a comment.
     *
     * @param commentId The UUID of the comment to dislike.
     * @param userId    The UUID of the user disliking the comment.
     * @throws NotFoundException        If the comment does not exist.
     * @throws InteractionBlockedException If the comment is already disliked by the user.
     */
    @Override
    public void addDislike(UUID commentId, UUID userId) {
        Optional<Comment> commentOp = commentRepository.findById(commentId);
        if (commentOp.isEmpty()) {
            throw new NotFoundException("CommentNotFoundException", "Fetching a comment", "Comment with id " + commentId + " does not exist.");
        }
        Comment comment = commentOp.get();
        if(comment.getDislikes().stream().filter(p -> p.getUserId().equals(userId)).collect(Collectors.toList()).isEmpty()){
            Interact interact = new Interact();
            interact.setUserId(userId);
            comment.getDislikes().add(interact);
            comment.setLikes(comment.getLikes().stream().filter(p -> !p.getUserId().equals(userId)).collect(Collectors.toList()));
        }
        else{
            throw new InteractionBlockedException("Comment is already disliked by the user.");
        }
        commentRepository.save(comment);
    }

    /**
     * Remove a user's interaction (like or dislike) from a comment.
     *
     * @param commentId The UUID of the comment to remove interaction from.
     * @param userId    The UUID of the user whose interaction should be removed.
     * @throws NotFoundException If the comment does not exist.
     */
    @Override
    public void removeInteraction(UUID commentId, UUID userId) {
        Optional<Comment> commentOp = commentRepository.findById(commentId);
        if (commentOp.isEmpty()) {
            throw new NotFoundException("CommentNotFoundException", "Fetching a comment", "Comment with id " + commentId + " does not exist.");
        }
        Comment comment = commentOp.get();
        comment.setLikes(comment.getLikes().stream().filter(p -> !p.getUserId().equals(userId)).collect(Collectors.toList()));
        comment.setDislikes(comment.getDislikes().stream().filter(p -> !p.getUserId().equals(userId)).collect(Collectors.toList()));
        commentRepository.save(comment);
    }
}
