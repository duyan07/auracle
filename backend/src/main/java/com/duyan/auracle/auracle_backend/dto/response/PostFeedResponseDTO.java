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
public class PostFeedResponseDTO {
    private String id;
    private String username;
    private String displayName;
    private String profileImageUrl;
    private String content;
    private String firstImageUrl;
    private Integer imageCount;
    private Integer likesCount;
    private Integer commentsCount;
    private LocalDateTime createdAt;
    private Boolean isLikedByCurrentUser;
}
