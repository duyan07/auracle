package com.duyan.auracle.auracle_backend.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class CreatePostRequest {

    @NotBlank(message = "Post content cannot be empty")
    @Size(min = 1, max = 2000, message = "Post must be between 1 and 2000 characters")
    private String content;

    @Size(max = 4, message = "Cannot attach more than 4 images")
    private List<@Pattern(regexp = "^https?://.*\\.(jpg|jpeg|png|gif|webp)$",
            message = "Image URLs must be valid") String> imageUrls;}
