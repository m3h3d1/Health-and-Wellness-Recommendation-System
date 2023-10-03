package com.healthapp.communityservice.services.implementations;

import com.healthapp.communityservice.entities.Comment;
import com.healthapp.communityservice.entities.Interact;
import com.healthapp.communityservice.models.commentdto.CommentCreateDTO;
import com.healthapp.communityservice.models.commentdto.CommentReadDTO;
import com.healthapp.communityservice.models.commentdto.CommentUpdateDTO;
import com.healthapp.communityservice.repositories.CommentRepository;
import com.healthapp.communityservice.services.interfaces.CommentService;
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

    public CommentServiceImpl(CommentRepository commentRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    @Override
    public void create(CommentCreateDTO commentCreateDTO) {
        Comment comment = commentMapper.getComment(commentCreateDTO);
        commentRepository.save(comment);
    }

    @Override
    public CommentReadDTO read(UUID commentId) {
        Optional<Comment> commentOp = commentRepository.findById(commentId);
        if (commentOp.isEmpty()) {
            return null;
        }
        return commentMapper.getCommentRead(commentOp.get());
    }

    @Override
    public List<CommentReadDTO> getCommentsByPostId(UUID postId) {
        List<Comment> comments = commentRepository.findAll().stream()
                .filter(c -> c.getParentPost().getPostId().equals(postId))
                .toList();
        return comments.stream()
                .map(commentMapper::getCommentRead)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentReadDTO> readAll() {
        return commentRepository.findAll().stream()
                .map(commentMapper::getCommentRead)
                .collect(Collectors.toList());
    }

    @Override
    public void update(UUID commentId, CommentUpdateDTO commentUpdateDTO) {
        Optional<Comment> commentOp = commentRepository.findById(commentId);
        if (commentOp.isEmpty()) {
            return;
        }
        Comment comment = commentOp.get();
        comment.setContent(commentUpdateDTO.getContent());
        commentRepository.save(comment);
    }

    @Override
    public void delete(UUID commentId) {
        read(commentId);
        commentRepository.deleteById(commentId);
    }

    @Override
    public void addLike(UUID commentId, UUID userId) {
        Optional<Comment> commentOp = commentRepository.findById(commentId);
        if (commentOp.isEmpty()) {
            return;
        }
        Comment comment = commentOp.get();
        if(comment.getLikes().stream().filter(p -> p.getUserId().equals(userId)).collect(Collectors.toList()).isEmpty()){
            Interact interact = new Interact();
            interact.setUserId(userId);
            comment.getLikes().add(interact);
            comment.setDislikes(comment.getLikes().stream().filter(p -> !p.getUserId().equals(userId)).collect(Collectors.toList()));
        }
        commentRepository.save(comment);
    }

    @Override
    public void addDislike(UUID commentId, UUID userId) {
        Optional<Comment> commentOp = commentRepository.findById(commentId);
        if (commentOp.isEmpty()) {
            return;
        }
        Comment comment = commentOp.get();
        if(comment.getDislikes().stream().filter(p -> p.getUserId().equals(userId)).collect(Collectors.toList()).isEmpty()){
            Interact interact = new Interact();
            interact.setUserId(userId);
            comment.getDislikes().add(interact);
            comment.setLikes(comment.getLikes().stream().filter(p -> !p.getUserId().equals(userId)).collect(Collectors.toList()));
        }
        commentRepository.save(comment);
    }

    @Override
    public void removeInteraction(UUID commentId, UUID userId) {
        Optional<Comment> commentOp = commentRepository.findById(commentId);
        if (commentOp.isEmpty()) {
            return;
        }
        Comment comment = commentOp.get();
        comment.setLikes(comment.getLikes().stream().filter(p -> !p.getUserId().equals(userId)).collect(Collectors.toList()));
        comment.setDislikes(comment.getDislikes().stream().filter(p -> !p.getUserId().equals(userId)).collect(Collectors.toList()));
        commentRepository.save(comment);
    }
}
