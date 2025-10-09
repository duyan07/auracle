package com.duyan.auracle.auracle_backend.controller;

import com.duyan.auracle.auracle_backend.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/upload")
public class UploadController {

    @Autowired
    private S3Service s3Service;

    @PostMapping("/profile-iamge")
    public ResponseEntity<Map<String, String>> uploadProfileImage(
            @RequestParam("file") MultipartFile file) {
        try {
            String imageUrl = s3Service.uploadFile(file, "profiles");
            return ResponseEntity.ok(Map.of("imageUrl", imageUrl));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("error", "Upload failed: " + e.getMessage()));
        }
    }

    @PostMapping("/post-image")
    public ResponseEntity<Map<String, String>> uplaodPostImage(
            @RequestParam("file") MultipartFile file) {
        try {
            String imageUrl = s3Service.uploadFile(file, "posts");
            return ResponseEntity.ok(Map.of("imageUrl", imageUrl));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("error", "Upload failed: " + e.getMessage()));
        }
    }
}
