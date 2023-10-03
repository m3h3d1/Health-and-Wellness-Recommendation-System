package com.healthapp.communityservice.services.implementations;

import com.healthapp.communityservice.entities.Follow;
import com.healthapp.communityservice.entities.Post;
import com.healthapp.communityservice.repositories.FollowRepository;
import com.healthapp.communityservice.repositories.FollowingRepository;
import com.healthapp.communityservice.services.interfaces.FollowingService;

import java.util.List;
import java.util.UUID;

public class FollowingServiceImpl implements FollowingService {

    private final FollowRepository followRepository;
    private final FollowingRepository followingRepository;

    public FollowingServiceImpl(FollowRepository followRepository, FollowingRepository followingRepository) {
        this.followRepository = followRepository;
        this.followingRepository = followingRepository;
    }

    @Override
    public void follow(UUID followerId, UUID followingId) {
        List<Follow> following = followRepository.findAll()
                .stream().filter(f -> f.getFollowerId().equals(followerId)).toList();
        boolean alreadyFollowing = false;
        for(Follow follow: following){
            if(follow.getFollowerId().equals(followingId)){
                alreadyFollowing = true;
                // Throw new exception
            }
        }

    }

    @Override
    public void unFollow(UUID followerId, UUID followingId) {

    }

    @Override
    public void following(UUID followerId) {

    }

    @Override
    public List<Post> followingFeed(UUID followerId) {
        return null;
    }
}
