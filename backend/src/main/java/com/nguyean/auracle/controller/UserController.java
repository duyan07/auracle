package com.nguyean.auracle.controller;

import com.nguyean.auracle.model.User;
import com.nguyean.auracle.model.UserRegistrationDto;

import com.nguyean.auracle.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{userId}")
    public User getUser(@PathVariable("userId") Long userId) {
        return userService.getUser(userId);
    }

    @GetMapping("{userId}/followers")
    public Set<User> getUserFollowers(@PathVariable("userId") Long userId) {
        return userService.getUser(userId).getFollowers();
    }

    @GetMapping("{userId}/following")
    public Set<User> getUserFollowing(@PathVariable("userId") Long userId) {
        return userService.getUser(userId).getFollowing();
    }

    @PostMapping
    public ResponseEntity<User> addUser(@Valid @RequestBody UserRegistrationDto registrationDto) {
        User user = new User(
                registrationDto.getUsername(),
                registrationDto.getDisplayName(),
                registrationDto.getBio(),
                registrationDto.getEmail(),
                registrationDto.getPassword()
        );

        return ResponseEntity.ok(userService.addUser(user));
    }

    @DeleteMapping("{userId}")
    public void deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
    }

    @PutMapping("{userId}")
    public void updateUser(
            @PathVariable("userId") Long userId,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "displayName", required = false) String displayName,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "password", required = false) String password) {
        if (username != null) userService.updateUsername(userId, username);
        if (displayName != null) userService.updateDisplayName(userId, displayName);
        if (email != null) userService.updateEmail(userId, email);
        if (password != null) userService.updatePassword(userId, password);
    }
}
