package com.nguyean.auracle.service;

import com.nguyean.auracle.model.User;

import com.nguyean.auracle.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("user not found"));
    }

    public User addUser(User user) {
        Optional<User> userByUsername = userRepository.findUserByUsername(user.getUsername());
        if (userByUsername.isPresent()) {
            throw new IllegalStateException("Username already taken");
        }

        Optional<User> userByEmail = userRepository.findUserByEmail(user.getEmail());
        if (userByEmail.isPresent()) {
            throw new IllegalStateException("Email already taken");
        }

        // TODO: Hash password before saving
        // String hashedPassword = passwordEncoder.encode(user.getPassword());
        // user.setPassword(hashedPassword);

        return userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        userRepository.delete(user);
    }

    @Transactional
    public void updateUsername(Long userId, String username) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("user not found"));
        String USERNAME_REGEX = "^[a-zA-Z0-9_]+$";
        if (!username.matches(USERNAME_REGEX)) {
            throw new IllegalArgumentException("Invalid username format");
        }
        Optional<User> userOptional = userRepository.findUserByUsername(username);
        if (user.getUsername().equals(username)) {
            throw new IllegalStateException("new username is the same as old");
        }
        if (userOptional.isPresent()) {
            throw new IllegalStateException("username taken");
        }
        user.setUsername(username);
    }

    @Transactional
    public void updateDisplayName(Long userId, String displayName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("user not found"));
        if (user.getDisplayName().equals(displayName)) {
            throw new IllegalStateException("new display name is the same as old");
        }
        user.setDisplayName(displayName);
    }

    @Transactional
    public void updateEmail(Long userId, String email) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("user not found"));
        String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        if (!email.matches(EMAIL_REGEX)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        Optional<User> userOptional = userRepository.findUserByEmail(email);
        if (user.getEmail().equals(email)) {
            throw new IllegalStateException("new email is the same as old");
        }
        if (userOptional.isPresent()) {
            throw new IllegalStateException("email taken");
        }
        user.setEmail(email);
    }

    @Transactional
    public void updatePassword(Long userId, String password) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("user not found"));
        if (user.getPassword().equals(password)) {
            throw new IllegalStateException("new password is the same as old");
        }
        user.setPassword(password);
    }
}
