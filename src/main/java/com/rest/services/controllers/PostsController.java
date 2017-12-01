package com.rest.services.controllers;

import java.util.List;
import com.rest.services.beans.Post;
import com.rest.services.dao.PostDao;
import com.rest.services.exceptions.PostNotFoundException;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class PostsController {

    @Autowired
    private PostDao postDao;

    @GetMapping("/users/{userId}/posts")
    public List<Post> retrieveAllPostsFromUser(@PathVariable String userId) {
        return postDao.findAllBy(userId);
    }

    @GetMapping("/users/{userId}/posts/{postId}")
    public Post retrievePostFromUserId(@PathVariable String userId, @PathVariable String postId) {
        return postDao.findBy(userId, postId).orElseThrow(() -> new PostNotFoundException("Post not found"));
    }

    @GetMapping("/posts")
    public List<Post> retrieveAllPosts() {
        return postDao.findAll();
    }
}
