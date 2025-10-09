package com.duyan.auracle.auracle_backend.controller;

import com.amazonaws.Response;
import com.duyan.auracle.auracle_backend.dto.request.CreatePostRequest;
import com.duyan.auracle.auracle_backend.dto.request.UpdatePostRequest;
import com.duyan.auracle.auracle_backend.dto.response.PageResponseDTO;
import com.duyan.auracle.auracle_backend.dto.response.PostFeedResponseDTO;
import com.duyan.auracle.auracle_backend.dto.response.PostResponseDTO;
import com.duyan.auracle.auracle_backend.mapper.PageMapper;
import com.duyan.auracle.auracle_backend.mapper.PostMapper;
import com.duyan.auracle.auracle_backend.model.Post;
import com.duyan.auracle.auracle_backend.model.User;
import com.duyan.auracle.auracle_backend.service.PostService;
import com.duyan.auracle.auracle_backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private PageMapper pageMapper;

    @PostMapping
    public ResponseEntity<PostResponseDTO> createPost(@Valid @RequestBody CreatePostRequest request) {
        String clerkUserId = getAuthenticatedUserId();
        Post post = postService.createPost(clerkUserId, request.getContent(), request.getImageUrls());
        PostResponseDTO dto = postMapper.toPostResponseDTO(post, post.getUserId());
        return ResponseEntity.status(201).body(dto);    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDTO> getPost(@PathVariable String postId) {
        String clerkUserId = getAuthenticatedUserId();
        User currentUser = userService.getUserByClerkIdOrThrow(clerkUserId);
        Post post = postService.getPostByIdOrThrow(postId);
        PostResponseDTO dto = postMapper.toPostResponseDTO(post, currentUser.getId());
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostResponseDTO>> getUserPosts(@PathVariable String userId) {
        String clerkUserId = getAuthenticatedUserId();
        User currentUser = userService.getUserByClerkIdOrThrow(clerkUserId);
        List<Post> posts = postService.getPostsByUser(userId);

        List<PostResponseDTO> dtos = posts.stream()
                .map(post -> postMapper.toPostResponseDTO(post, currentUser.getId()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/feed")
    public ResponseEntity<PageResponseDTO<PostFeedResponseDTO>> getFeed(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        String clerkUserId = getAuthenticatedUserId();
        User currentUser = userService.getUserByClerkIdOrThrow(clerkUserId);

        Page<Post> posts = postService.getFeed(page, size);
        PageResponseDTO<PostFeedResponseDTO> response = pageMapper.toPageResponseDTO(
                posts,
                post -> postMapper.toPostFeedResponseDTO(post, currentUser.getId())
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostResponseDTO> updatePost(
            @PathVariable String postId,
            @Valid @RequestBody UpdatePostRequest request) {
        String clerkUserId = getAuthenticatedUserId();
        Post updatedPost = postService.updatePost(clerkUserId, postId, request.getContent());

        PostResponseDTO dto = postMapper.toPostResponseDTO(updatedPost, postId);
        return ResponseEntity.ok(dto);
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
