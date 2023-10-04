package com.healthapp.communityservice.services.interfaces;

import com.healthapp.communityservice.entities.Following;
import com.healthapp.communityservice.entities.Post;
import com.healthapp.communityservice.models.postdto.PostReadDTO;

import java.util.List;
import java.util.UUID;

public interface ConnectionService {
    // Connection CRUD Operations
    public void follow(UUID followerId, UUID followingId);
    public void unFollow(UUID followerId, UUID followingId);
    public List<Following> following(UUID followerId);
    public List<PostReadDTO> followingFeed(UUID followerId);
}
