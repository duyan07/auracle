package com.duyan.auracle.auracle_backend.service;

import com.duyan.auracle.auracle_backend.model.User;
import com.duyan.auracle.auracle_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createOrGetUser(String clerkUserId, String username, String email, String displayName) {
        Optional<User> existingUser = userRepository.findByClerkUserId(clerkUserId);

        if (existingUser.isPresent()) {
            return existingUser.get();
        }

        User newUser = new User();
        newUser.setClerkUserId(clerkUserId);
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setDisplayName(displayName);
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setUpdatedAt(LocalDateTime.now());

        return userRepository.save(newUser);
    }

    public Optional<User> getUserByClerkId(String clerkUserId) {
        return userRepository.findByClerkUserId(clerkUserId);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public User updateUser(String clerkUserId, User updates) {
        User user = userRepository.findByClerkUserId(clerkUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (updates.getDisplayName() != null) {
            user.setDisplayName(updates.getDisplayName());
        }
        if (updates.getBio() != null) {
            user.setBio(updates.getBio());
        }
        if (updates.getProfileImageUrl() != null) {
            user.setProfileImageUrl(updates.getProfileImageUrl());
        }
        if (updates.getBannerImageUrl() != null) {
            user.setBannerImageUrl(updates.getBannerImageUrl());
        }

        user.setUpdatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    public boolean isUsernameAvailable(String username) {
        return !userRepository.existsByUsername(username);
    }

    public void incrementPostCount(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setPostsCount(user.getPostsCount() + 1);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    public void decrementPostCount(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setPostsCount(user.getPostsCount() - 1);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }
}
