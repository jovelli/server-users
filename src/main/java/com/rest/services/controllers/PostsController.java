package com.rest.services.controllers;

import java.util.List;
import javax.validation.Valid;

import com.rest.services.beans.Post;
import com.rest.services.beans.UserPost;
import com.rest.services.dao.PostDao;
import com.rest.services.dao.UserDao;
import com.rest.services.exceptions.PostNotFoundException;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class PostsController {

    @Autowired
    private PostDao postDao;

    @Autowired
    private UserDao userDao;

    @GetMapping("/users/{userId}/posts")
    public List<Post> retrieveAllPostsFromUser(@PathVariable String userId) {
        return postDao.findAllBy(userId);
    }

    @GetMapping("/users/{userId}/posts/{postId}")
    public Post retrievePostFromUserId(@PathVariable String userId, @PathVariable String postId) {
        return postDao.findBy(userId, postId).orElseThrow(() -> new PostNotFoundException("Post not found"));
    }

    @GetMapping("/posts")
    public List<UserPost> retrieveAllPosts() {
        return postDao.findAll();
    }

    @PostMapping("/users/{userId}/posts")
    public ResponseEntity handlePost(@PathVariable String userId, @Valid @RequestBody Post post) {
        return postDao.save(userId, post).map(savedPost ->
            ResponseEntity.created(ServletUriComponentsBuilder.
                fromCurrentRequest().
                path("{postId}").
                buildAndExpand(savedPost.getId()).
                toUri()
            ).build()
        ).orElse(
            ResponseEntity.badRequest().build()
        );
    }
}
