package com.duyan.auracle.auracle_backend.mapper;

import com.duyan.auracle.auracle_backend.dto.response.PostFeedResponseDTO;
import com.duyan.auracle.auracle_backend.dto.response.PostResponseDTO;
import com.duyan.auracle.auracle_backend.model.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    public PostResponseDTO toPostResponseDTO(Post post, String currentUserId, boolean isLiked) {
        if (post == null) return null;

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
                .isOwnPost(post.getUserId().equals(currentUserId))
                .build();
    }

    public PostFeedResponseDTO toPostFeedResponseDTO(Post post, boolean isLiked) {
        if (post == null) return null;

        String firstImage = post.getImageUrls() != null && !post.getImageUrls().isEmpty()
                ? post.getImageUrls().get(0)
                : null;

        return PostFeedResponseDTO.builder()
                .id(post.getId())
                .username(post.getUsername())
                .displayName(post.getDisplayName())
                .profileImageUrl(post.getProfileImageUrl())
                .content(post.getContent())
                .firstImageUrl(firstImage) // Only first image
                .imageCount(post.getImageUrls() != null ? post.getImageUrls().size() : 0)
                .likesCount(post.getLikesCount())
                .commentsCount(post.getCommentsCount())
                .createdAt(post.getCreatedAt())
                .isLikedByCurrentUser(isLiked)
                .build();
    }
}
