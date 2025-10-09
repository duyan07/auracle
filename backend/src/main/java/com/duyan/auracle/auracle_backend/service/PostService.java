package com.duyan.auracle.auracle_backend.service;

import com.duyan.auracle.auracle_backend.model.Post;
import com.duyan.auracle.auracle_backend.model.User;
import com.duyan.auracle.auracle_backend.repository.PostRepository;
import com.duyan.auracle.auracle_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    public Post createPost(String clerkUserId, String content, List<String> imageUrls) {
        User user = userService.getUserByClerkId(clerkUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = new Post();
        post.setUserId(user.getId());
        post.setUsername(user.getUsername());
        post.setDisplayName(user.getDisplayName());
        post.setProfileImageUrl(user.getProfileImageUrl());
        post.setContent(content);
        post.setImageUrls(imageUrls != null ? imageUrls : List.of());
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        Post savedPost = postRepository.save(post);

        userService.incrementPostCount(user.getId());

        return savedPost;
    }

    public Optional<Post> getPostById(String postId) {
        return postRepository.findById(postId);
    }

    public List<Post> getPostsByUser(String userId) {
        return postRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public Page<Post> getPostsByUserPaginated(String userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findByUserId(userId, pageable);
    }

    public Page<Post> getFeed(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    public void deletePost(String postId, String clerkUserId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        User user = userService.getUserByClerkId(clerkUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!post.getUserId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized to delete this post");
        }

        postRepository.deleteById(postId);
        userService.decrementPostCount(user.getId());
    }

    public Post updatePost(String postId, String clerkUserId, String newContent) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new RuntimeException("Post not found"));

        User user = userService.getUserByClerkId(clerkUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!post.getUserId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized to edit this post");
        }

        post.setContent(newContent);
        post.setUpdatedAt(LocalDateTime.now());

        return postRepository.save(post);
    }

    public void increaseLikeCount(String postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        post.setLikesCount(post.getLikesCount() + 1);
        post.setUpdatedAt(LocalDateTime.now());
        postRepository.save(post);
    }
}
