package com.duyan.auracle.auracle_backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "posts")
public class Post {

    @Id
    private String id;

    @Indexed
    private String userId;

    private String username;
    private String displayName;
    private String profileImageUrl;

    private String content;
    private List<String> imageUrls = new ArrayList<>();

    private Integer likesCount = 0;
    private Integer commentsCount = 0;
    private Integer respostsCount = 0;

    @Indexed
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
}
