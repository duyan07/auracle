package com.duyan.auracle.auracle_backend.controller;

import com.duyan.auracle.auracle_backend.model.User;
import com.duyan.auracle.auracle_backend.service.UserService;
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

    @PostMapping("/me")
    public ResponseEntity<User> createOrGetCurrentUser(@RequestBody Map<String, String> userData) {
        String clerkUserId = getAuthenticatedUserId();
        String username = userData.get("username");
        String email = userData.get("email");
        String displayName = userData.get("displayName");

        User user = userService.createOrGetUser(clerkUserId, username, email, displayName);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser() {
        String clerkUserId = getAuthenticatedUserId();
        User user = userService.getUserByClerkId(clerkUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(user);
    }

    @PutMapping("/me")
    public ResponseEntity<User> updateCurrentUser(@RequestBody User updates) {
        String clerkUserId = getAuthenticatedUserId();
        User updatedUser = userService.updateUser(clerkUserId, updates);
        return ResponseEntity.ok(updatedUser);
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
