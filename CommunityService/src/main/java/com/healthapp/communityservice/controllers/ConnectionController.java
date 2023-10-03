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
        try {
            connectionService.follow(followerId, followingId);
            return ResponseEntity.ok("Successfully followed user.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/unfollow")
    public ResponseEntity<String> unfollowUser(@RequestParam UUID followerId, @RequestParam UUID followingId) {
        try {
            connectionService.unFollow(followerId, followingId);
            return ResponseEntity.ok("Successfully unfollowed user.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/following/{followerId}")
    public ResponseEntity<?> getFollowing(@PathVariable UUID followerId) {
        try {
            return ResponseEntity.ok(connectionService.following(followerId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/feed/{followerId}")
    public ResponseEntity<?> getFollowingFeed(@PathVariable UUID followerId) {
        try {
            return ResponseEntity.ok(connectionService.followingFeed(followerId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
