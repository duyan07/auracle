package com.duyan.auracle.auracle_backend.controller;

import com.duyan.auracle.auracle_backend.model.Post;
import com.duyan.auracle.auracle_backend.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Map<String, Object> postData) {
        String clerkUserId = getAuthenticatedUserId();
        String content = (String) postData.get("content");
        List<String> imageUrls = (List<String>) postData.get("imageUrls");

        Post post = postService.createPost(clerkUserId, content, imageUrls);
        return ResponseEntity.status(201).body(post);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPost(@PathVariable String postId) {
        Post post = postService.getPostById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        return ResponseEntity.ok(post);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Post>> getUserPosts(@PathVariable String userId) {
        List<Post> posts = postService.getPostsByUser(userId);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/feed")
    public ResponseEntity<Page<Post>> getFeed(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Page<Post> posts = postService.getFeed(page, size);
        return ResponseEntity.ok(posts);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Post> updatePost(
            @PathVariable String postId,
            @RequestBody Map<String, String> updates) {
        String clerkUserId = getAuthenticatedUserId();
        String newContent = updates.get("content");

        Post updatedPost = postService.updatePost(postId, clerkUserId, newContent);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable String postId) {
        String clerkUserId = getAuthenticatedUserId();
        postService.deletePost(postId, clerkUserId);
        return ResponseEntity.noContent().build();
    }

    private String getAuthenticatedUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (String) auth.getPrincipal();
    }
}
