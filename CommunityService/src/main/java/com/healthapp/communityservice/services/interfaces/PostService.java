package com.healthapp.communityservice.services.interfaces;

import com.healthapp.communityservice.models.postdto.PostCreateDTO;
import com.healthapp.communityservice.models.postdto.PostReadDTO;
import com.healthapp.communityservice.models.postdto.PostUpdateDTO;

import java.util.List;
import java.util.UUID;

public interface PostService {
    // Post crud operations
    public void create(PostCreateDTO postCreateDTO);
    public PostReadDTO read(UUID postId);
    public List<PostReadDTO> readAll();
    public List<PostReadDTO> findByUser(UUID userId);
    public void update(UUID postId, UUID requestUserId, PostUpdateDTO postUpdateDTO);
    public void delete(UUID postId, UUID requestUserId);

    // Partial operations
    public void addLike(UUID postId, UUID userId);
    public void addDislike(UUID postId, UUID userId);
    public void addFollower(UUID postId, UUID userId);
    public void removeFollower(UUID postId, UUID userId);
    public void removeInteraction(UUID postId, UUID userId);
}
