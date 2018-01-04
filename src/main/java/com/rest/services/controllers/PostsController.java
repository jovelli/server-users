package com.rest.services.controllers;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;

import com.rest.services.beans.Post;
import com.rest.services.beans.User;
import com.rest.services.dao.PostRepository;
import com.rest.services.dao.UserRepository;
import com.rest.services.exceptions.PostNotFoundException;
import com.rest.services.exceptions.UserNotFoundException;
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
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/posts")
    public List<Post> retrieveAllPosts() {
        return postRepository.findAll();
    }

    @GetMapping("/users/{userId}/posts")
    public List<Post> retrieveAllPostsFromUser(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not Found"));
        return user.getPosts();
    }

    @GetMapping("/users/{userId}/posts/{postId}")
    public ResponseEntity retrievePostFromUserId(@PathVariable Long userId, @PathVariable Long postId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not Found"));

        for (Post post: user.getPosts()) {
            if (post.getId() == postId) {
                return ResponseEntity.ok(post);
            }
        }

        return ResponseEntity.notFound().build(); //postDao.findBy(userId, postId).orElseThrow(() -> new PostNotFoundException("Post not found"));
    }

    @PostMapping("/users/{userId}/posts")
    public ResponseEntity handlePost(@PathVariable Long userId, @Valid @RequestBody Post post) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        post.setUser(user);

        return Optional.of(postRepository.save(post)).map(savedPost ->
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
