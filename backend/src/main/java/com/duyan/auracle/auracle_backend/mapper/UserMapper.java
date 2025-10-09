package com.duyan.auracle.auracle_backend.mapper;

import com.duyan.auracle.auracle_backend.dto.response.UserProfileResponseDTO;
import com.duyan.auracle.auracle_backend.dto.response.UserResponseDTO;
import com.duyan.auracle.auracle_backend.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponseDTO toUserResponseDTO(User user) {
        if (user == null) return null;

        return UserResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .displayName(user.getDisplayName())
                .bio(user.getBio())
                .profileImageUrl(user.getProfileImageUrl())
                .bannerImageUrl(user.getBannerImageUrl())
                .followersCount(user.getFollowersCount())
                .followingCount(user.getFollowingCount())
                .postsCount(user.getPostsCount())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public UserProfileResponseDTO toUserProfileResponseDTO(User user) {
        if (user == null) return null;

        return UserProfileResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .displayName(user.getDisplayName())
                .bio(user.getBio())
                .profileImageUrl(user.getProfileImageUrl())
                .bannerImageUrl(user.getBannerImageUrl())
                .followersCount(user.getFollowersCount())
                .followingCount(user.getFollowingCount())
                .postsCount(user.getPostsCount())
                .createdAt(user.getCreatedAt())
                .email(user.getEmail())
                .clerkUserId(user.getClerkUserId())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
