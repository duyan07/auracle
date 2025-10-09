package com.duyan.auracle.auracle_backend.service;

import com.duyan.auracle.auracle_backend.dto.request.UpdateUserRequest;
import com.duyan.auracle.auracle_backend.exception.BadRequestException;
import com.duyan.auracle.auracle_backend.exception.ResourceNotFoundException;
import com.duyan.auracle.auracle_backend.model.User;
import com.duyan.auracle.auracle_backend.repository.UserRepository;
import jakarta.validation.Valid;
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

    public User getUserByClerkIdOrThrow(String clerkUserId) {
        return userRepository.findByClerkUserId(clerkUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "clerkUserId", clerkUserId));
    }

    public User getUserByUsernameOrThrow(String username) {
        return getUserByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
    }

    public User getUserByIdOrThrow(String id) {
        return getUserById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    public User updateUser(String clerkUserId, @Valid UpdateUserRequest updates) {
        User user = userRepository.findByClerkUserId(clerkUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "clerkUserId", clerkUserId));

        if (updates.getDisplayName() != null && updates.getDisplayName().trim().isEmpty()) {
            throw new BadRequestException("Display name cannot be empty");
        }

        if (updates.getBio() != null && updates.getBio().length() > 200) {
            throw new BadRequestException("Bio cannot be longer than 200 characters");
        }

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
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        user.setPostsCount(user.getPostsCount() + 1);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    public void decrementPostCount(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        user.setPostsCount(user.getPostsCount() - 1);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    public void incrementFollowersCount(String userId) {
        User user = getUserByIdOrThrow(userId);
        user.setFollowersCount(user.getFollowersCount() + 1);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    public void decrementFollowersCount(String userId) {
        User user = getUserByIdOrThrow(userId);
        user.setFollowersCount(Math.max(0, user.getFollowersCount() - 1));
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    public void incrementFollowingCount(String userId) {
        User user = getUserByIdOrThrow(userId);
        user.setFollowingCount(user.getFollowingCount() + 1);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    public void decrementFollowingCount(String userId) {
        User user = getUserByIdOrThrow(userId);
        user.setFollowingCount(Math.max(0, user.getFollowingCount() - 1));
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }
}
