package com.duyan.auracle.auracle_backend.service;

import com.duyan.auracle.auracle_backend.exception.BadRequestException;
import com.duyan.auracle.auracle_backend.exception.ResourceNotFoundException;
import com.duyan.auracle.auracle_backend.model.Like;
import com.duyan.auracle.auracle_backend.model.Post;
import com.duyan.auracle.auracle_backend.model.User;
import com.duyan.auracle.auracle_backend.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Transactional
    public void likePost(String postId, String clerkUserId) {
        Post post = postService.getPostByIdOrThrow(postId);
        User user = userService.getUserByIdOrThrow(clerkUserId);

        if (likeRepository.existsByUserIdAndPostId(user.getId(), postId)) {
            throw new BadRequestException("You have already liked this post");
        }

        Like like = new Like();
        like.setUserId(user.getId());
        like.setPostId(postId);
        likeRepository.save(like);
        postService.incrementLikeCount(postId);
    }

    @Transactional
    public void unlikePost(String postId, String clerkUserId) {
        Post post = postService.getPostByIdOrThrow(postId);
        User user = userService.getUserByIdOrThrow(clerkUserId);

        if (likeRepository.existsByUserIdAndPostId(user.getId(), postId)) {
            throw new BadRequestException("You have not liked this post");
        }

        likeRepository.deleteByUserIdAndPostId(user.getId(), postId);
        postService.decrementLikeCount(postId);
    }

    public boolean hasUserLikedPost(String userId, String postId) {
        return likeRepository.existsByUserIdAndPostId(userId, postId);
    }

    public boolean hasCurrentUserLikedPost(String postId, String clerkUserId) {
        User user = userService.getUserByIdOrThrow(clerkUserId);
        return likeRepository.existsByUserIdAndPostId(user.getId(), postId);
    }
}
