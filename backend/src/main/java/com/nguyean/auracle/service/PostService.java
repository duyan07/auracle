package com.nguyean.auracle.service;

import com.nguyean.auracle.model.Post;
import com.nguyean.auracle.model.User;

import com.nguyean.auracle.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAllByOrderByDateTimeDesc();
    }

    public List<Post> getUserPosts(Long userId) {
        return postRepository.findByUserIdOrderByDateTimeDesc(userId);
    }

    public Post addPost(Post post) {
        return postRepository.save(post);
    }

    public void deletePost(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new IllegalStateException("Post not found");
        }
        postRepository.deleteById(postId);
    }

    public void addPostLike(Long postId, User user) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalStateException("Post not found"));
        if (post.getLikes().contains(user)) {
            throw new IllegalStateException("Post already liked by user");
        }
        post.addLike(user);
        postRepository.save(post);
    }

    public void removePostLike(Long postId, User user) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalStateException("Post not found"));
        if (!post.getLikes().contains(user)) {
            throw new IllegalStateException("Post not liked by user");
        }
        post.removeLike(user);
        postRepository.save(post);
    }

    @Transactional
    public void editPostMsg(Long postId, String msg) {
        if (msg == null || msg.trim().isEmpty()) {
            throw new IllegalArgumentException("Message cannot be empty");
        }
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalStateException("Post not found"));
        post.setMessage(msg);
    }
}
