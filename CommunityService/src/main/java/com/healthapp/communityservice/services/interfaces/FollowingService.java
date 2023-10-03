package com.healthapp.communityservice.services.interfaces;

import com.healthapp.communityservice.entities.Post;

import java.util.List;
import java.util.UUID;

public interface FollowingService {
    public void follow(UUID followerId, UUID followingId);
    public void unFollow(UUID followerId, UUID followingId);
    public void following(UUID followerId);
    public List<Post> followingFeed(UUID followerId);
}
