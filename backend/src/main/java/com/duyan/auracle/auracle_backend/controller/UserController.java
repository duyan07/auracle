package com.duyan.auracle.auracle_backend.controller;

import com.duyan.auracle.auracle_backend.dto.request.CreateUserRequest;
import com.duyan.auracle.auracle_backend.dto.request.UpdateUserRequest;
import com.duyan.auracle.auracle_backend.dto.response.UserProfileResponseDTO;
import com.duyan.auracle.auracle_backend.dto.response.UserResponseDTO;
import com.duyan.auracle.auracle_backend.mapper.UserMapper;
import com.duyan.auracle.auracle_backend.model.User;
import com.duyan.auracle.auracle_backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/me")
    public ResponseEntity<UserProfileResponseDTO> createOrGetCurrentUser(
            @Valid @RequestBody CreateUserRequest request) {
        String clerkUserId = getAuthenticatedUserId();

        User user = userService.createOrGetUser(
                clerkUserId,
                request.getUsername(),
                request.getEmail(),
                request.getDisplayName()
        );

        return ResponseEntity.ok(userMapper.toUserProfileResponseDTO(user));
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponseDTO> getCurrentUser() {
        String clerkUserId = getAuthenticatedUserId();
        User user = userService.getUserByClerkIdOrThrow(clerkUserId);
        return ResponseEntity.ok(userMapper.toUserProfileResponseDTO(user));
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserResponseDTO> getUserByUsername(@PathVariable String username) {
        String clerkUserId = getAuthenticatedUserId();
        User user = userService.getUserByClerkIdOrThrow(clerkUserId);
        return ResponseEntity.ok(userMapper.toUserResponseDTO(user));
    }

    @PutMapping("/me")
    public ResponseEntity<UserProfileResponseDTO> updateCurrentUser(
            @Valid @RequestBody UpdateUserRequest request) {
        String clerkUserId = getAuthenticatedUserId();
        User updatedUser = userService.updateUser(clerkUserId, request);
        return ResponseEntity.ok(userMapper.toUserProfileResponseDTO(updatedUser));
    }

    @GetMapping("/check-username/{username}")
    public ResponseEntity<Map<String, Boolean>> checkUsername(@PathVariable String username) {
        boolean available = userService.isUsernameAvailable(username);
        return ResponseEntity.ok(Map.of("available", available));
    }

    private String getAuthenticatedUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (String) auth.getPrincipal();
    }
}
