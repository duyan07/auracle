package com.nguyean.auracle.controller;

import com.nguyean.auracle.model.Post;
import com.nguyean.auracle.model.User;

import com.nguyean.auracle.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping()
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/user/{userId}")
    public List<Post> getUserPosts(@PathVariable("userId") Long userId) {
        return postService.getUserPosts(userId);
    }

    @PostMapping
    public Post addPost(@RequestBody Post post) {
        return postService.addPost(post);
    }

    @PostMapping("/{postId}/like")
    public void addPostLike(@PathVariable("postId") Long postId,
                            @RequestBody User user) {
        postService.addPostLike(postId, user);
    }

    @PostMapping("/{postId}/unlike")
    public void removePostLike(@PathVariable("postId") Long postId,
                               @RequestBody User user) {
        postService.removePostLike(postId, user);
    }

    @DeleteMapping("{postId}")
    public void deletePost(@PathVariable("postId") Long postId) {
        postService.deletePost(postId);
    }

    @PutMapping("{postId}")
    public void updatePostMsg(@PathVariable("postId") Long postId,
                              @RequestBody String msg) {
        postService.editPostMsg(postId, msg);
    }
}
