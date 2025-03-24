package com.nguyean.auracle.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    private String displayName;
    @Transient
    private String profileImg;
    private String bio;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "followers",
            joinColumns = @JoinColumn(name = "followed_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id")
    )
    private Set<User> followers = new HashSet<>();
    @ManyToMany(mappedBy = "following")
    private Set<User> following = new HashSet<>();
    @ManyToMany(mappedBy = "likes")
    private Set<Post> likedPosts = new HashSet<>();
    @Column(nullable = false, unique = true)
    private String email;
    @Transient
    @Column(nullable = false)
    private String passwordHash;

    public User() {}

    public User(String username,
                String displayName,
                String bio,
                String email,
                String password) {
        this.username = username;
        this.displayName = displayName;
        this.bio = bio;
        this.email = email;
        this.passwordHash = password;
        this.posts = new ArrayList<>();
        this.followers = new HashSet<>();
        this.following = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Set<User> getFollowing() {
        return following;
    }

    public void followUser(User user) {
        this.following.add(user);
        user.followed(this);
    }

    public void unfollowUser(User user) {
        this.following.remove(user);
        user.unfollowed(this);
    }

    public Set<User> getFollowers() {
        return followers;
    }

    public void followed(User user) {
        this.followers.add(user);
        user.getFollowing().add(this);
    }

    public void unfollowed(User user) {
        this.followers.remove(user);
        user.getFollowing().remove(this);
    }

    public Set<Post> getLikedPosts() {
        return likedPosts;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return passwordHash;
    }

    public void setPassword(String password) {
        this.passwordHash = password;
    }

    @PreRemove
    private void removeUserFromFollowers() {
        for (User follower : followers) {
            follower.getFollowing().remove(this);
        }
        for (User following : following) {
            following.getFollowers().remove(this);
        }
    }
}
