package com.duyan.auracle.auracle_backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "likes")
@CompoundIndex(name = "user_post_idx", def = "{'userId': 1, 'postId': 1}", unique = true)
public class Like {

    @Id
    private String id;

    @Indexed
    private String userId;

    @Indexed
    private String postId;

    private LocalDateTime createdAt = LocalDateTime.now();
}
