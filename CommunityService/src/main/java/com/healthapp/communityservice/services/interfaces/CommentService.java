package com.healthapp.communityservice.services.interfaces;

import com.healthapp.communityservice.models.commentdto.CommentCreateDTO;
import com.healthapp.communityservice.models.commentdto.CommentReadDTO;
import com.healthapp.communityservice.models.commentdto.CommentUpdateDTO;
import java.util.List;
import java.util.UUID;

public interface CommentService {

    // Comment CRUD operations
    void create(CommentCreateDTO commentCreateDTO);
    CommentReadDTO read(UUID commentId);
    List<CommentReadDTO> getCommentsByPostId(UUID postId);
    List<CommentReadDTO> readAll();
    void update(UUID commentId, CommentUpdateDTO commentUpdateDTO);
    void delete(UUID commentId);

    // Comment partial operations
    void addLike(UUID commentId, UUID userId);
    void addDislike(UUID commentId, UUID userId);
    void removeInteraction(UUID commentId, UUID userId);
}