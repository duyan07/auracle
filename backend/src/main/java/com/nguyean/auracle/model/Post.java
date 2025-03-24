package com.nguyean.auracle.model;

import jakarta.persistence.*;

import java.time.*;
import java.util.*;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @SequenceGenerator(
            name = "post_sequence",
            sequenceName = "post_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "post_sequence"
    )
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String message;
    private LocalDateTime dateTime;
    @Transient
    private String image;
    @ManyToMany
    @JoinTable(
            name = "post_likes",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> likes = new HashSet<>();

    public Post() {}

    public Post(User user,
                String message,
                LocalDateTime dateTime) {
        this.user = user;
        this.message = message;
        this.dateTime = dateTime;
        this.likes = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Message cannot be empty");
        }
        this.message = message;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Set<User> getLikes() {
        return likes;
    }

    public void addLike(User user) {
        this.likes.add(user);
        user.getLikedPosts().add(this);
    }

    public void removeLike(User user) {
        this.likes.remove(user);
        user.getLikedPosts().remove(this);
    }

    @Override
    public String toString() {
        return String.format("%s: " + user.getUsername() + ": %s", dateTime, message);
    }
}
