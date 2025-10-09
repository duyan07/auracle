package com.duyan.auracle.auracle_backend.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UpdatePostRequest {

    @NotBlank(message = "Post content cannot be empty")
    @Size(min = 1, max = 2000, message = "Post must be between 1 and 2000 characters")
    private String content;
}
