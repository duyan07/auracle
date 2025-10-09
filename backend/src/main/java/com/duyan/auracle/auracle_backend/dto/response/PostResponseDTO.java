package com.duyan.auracle.auracle_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDTO {
    private String id;

    private String userId;
    private String username;
    private String displayName;
    private String profileImageUrl;

    private String content;
    private List<String> imageUrls;

    private Integer likesCount;
    private Integer commentsCount;
    private Integer repostsCount;

    private LocalDateTime createdAt;

    private Boolean isLikedByCurrentUser;
    private Boolean isOwnPost;
}
