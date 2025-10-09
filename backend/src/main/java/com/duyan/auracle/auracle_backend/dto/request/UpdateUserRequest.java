package com.duyan.auracle.auracle_backend.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UpdateUserRequest {

    @Size(min = 1, max = 100, message = "Display name cannot exceed 100 characters")
    private String displayName;

    @Size(max = 200, message = "Bio cannot exceed 2-- characters")
    private String bio;

    @Pattern(regexp = "^https?://.*\\.(jpg|jpeg|png|gif|webp)$",
            message = "Profile image must be a valid image URL")
    private String profileImageUrl;

    @Pattern(regexp = "^https?://.*\\.(jpg|jpeg|png|gif|webp)$",
            message = "Banner image must be a valid image URL")
    private String bannerImageUrl;
}