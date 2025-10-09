package com.duyan.auracle.auracle_backend.mapper;

import com.duyan.auracle.auracle_backend.dto.response.PostFeedResponseDTO;
import com.duyan.auracle.auracle_backend.dto.response.PostResponseDTO;
import com.duyan.auracle.auracle_backend.model.Post;
import com.duyan.auracle.auracle_backend.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    @Autowired
    private LikeService likeService;

    public PostResponseDTO toPostResponseDTO(Post post, String currentUserId) {
        if (post == null) return null;

        boolean isLiked = likeService.hasUserLikedPost(currentUserId, post.getId());
        boolean isOwnPost = post.getUserId().equals(currentUserId);

        return PostResponseDTO.builder()
                .id(post.getId())
                .userId(post.getUserId())
                .username(post.getUsername())
                .displayName(post.getDisplayName())
                .profileImageUrl(post.getProfileImageUrl())
                .content(post.getContent())
                .imageUrls(post.getImageUrls())
                .likesCount(post.getLikesCount())
                .commentsCount(post.getCommentsCount())
                .createdAt(post.getCreatedAt())
                .isLikedByCurrentUser(isLiked)
                .isOwnPost(isOwnPost)
                .build();
    }

    public PostFeedResponseDTO toPostFeedResponseDTO(Post post, String currentUserId) {
        if (post == null) return null;

        boolean isLiked = likeService.hasUserLikedPost(currentUserId, post.getId());

        String firstImage = post.getImageUrls() != null && !post.getImageUrls().isEmpty()
                ? post.getImageUrls().get(0)
                : null;

        return PostFeedResponseDTO.builder()
                .id(post.getId())
                .username(post.getUsername())
                .displayName(post.getDisplayName())
                .profileImageUrl(post.getProfileImageUrl())
                .content(post.getContent())
                .firstImageUrl(firstImage)
                .imageCount(post.getImageUrls() != null ? post.getImageUrls().size() : 0)
                .likesCount(post.getLikesCount())
                .commentsCount(post.getCommentsCount())
                .createdAt(post.getCreatedAt())
                .isLikedByCurrentUser(isLiked)
                .build();
    }
}
