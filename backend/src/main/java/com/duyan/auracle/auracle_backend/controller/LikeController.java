package com.duyan.auracle.auracle_backend.controller;

import com.duyan.auracle.auracle_backend.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/posts/{postId}/likes")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping
    public ResponseEntity<Map<String, String>> likePost(@PathVariable String postId) {
        String clerkUserId = getAuthenticatedUserId();
        likeService.likePost(postId, clerkUserId);
        return ResponseEntity.ok(Map.of("message", "Post liked successfully"));
    }

    @DeleteMapping
    public ResponseEntity<Map<String, String>> unlikePost(@PathVariable String postId) {
        String clerkUserId = getAuthenticatedUserId();
        likeService.unlikePost(postId, clerkUserId);
        return ResponseEntity.ok(Map.of("message", "Post unliked successfully"));
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, Boolean>> getLikeStatus(@PathVariable String postId) {
        String clerkUserId = getAuthenticatedUserId();
        boolean liked = likeService.hasCurrentUserLikedPost(postId, clerkUserId);
        return ResponseEntity.ok(Map.of("liked", liked));
    }

    private String getAuthenticatedUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (String) auth.getPrincipal();
    }
}
