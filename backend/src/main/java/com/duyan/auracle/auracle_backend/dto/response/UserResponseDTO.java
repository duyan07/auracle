package com.duyan.auracle.auracle_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private String id;
    private String username;
    private String displayName;
    private String bio;
    private String profileImageUrl;
    private String bannerImageUrl;
    private Integer followersCount;
    private Integer followingCount;
    private Integer postsCount;
    private LocalDateTime createdAt;
}
