package com.healthapp.communityservice.controllers;

import com.healthapp.communityservice.services.interfaces.ConnectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/community/connections")
public class ConnectionController {

    private final ConnectionService connectionService;

    public ConnectionController(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    @PostMapping("/follow")
    public ResponseEntity<String> followUser(@RequestParam UUID followerId, @RequestParam UUID followingId) {
        connectionService.follow(followerId, followingId);
        return ResponseEntity.ok("Successfully followed user.");
    }

    @PostMapping("/unfollow")
    public ResponseEntity<String> unfollowUser(@RequestParam UUID followerId, @RequestParam UUID followingId) {
        connectionService.unFollow(followerId, followingId);
        return ResponseEntity.ok("Successfully unfollowed user.");
    }

    @GetMapping("/following/{followerId}")
    public ResponseEntity<?> getFollowing(@PathVariable UUID followerId) {
        return ResponseEntity.ok(connectionService.following(followerId));
    }

    @GetMapping("/feed/{followerId}")
    public ResponseEntity<?> getFollowingFeed(@PathVariable UUID followerId) {
        return ResponseEntity.ok(connectionService.followingFeed(followerId));
    }
}
