package com.rest.services.dao;

import com.rest.services.beans.Post;
import com.rest.services.beans.UserPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PostDao {

    private static Map<String, List<Post>> posts = new HashMap<>();

    @Autowired
    private UserDao userDao;

    static {
        List<Post> postsUser1 = new ArrayList<>();
        postsUser1.add(new Post("1","This is my first post here", new Date()));
        postsUser1.add(new Post("2","another hater spreading hate", new Date()));
        postsUser1.add(new Post("3","hi", new Date()));

        posts.put("1", postsUser1);

        List<Post> postsUser2 = new ArrayList<>();
        postsUser2.add(new Post("1","Hi Animals, Aardvark", new Date()));
        postsUser2.add(new Post("2","Kiwi fruit is tasteful", new Date()));
        postsUser2.add(new Post("3","Take some rest", new Date()));

        posts.put("2", postsUser2);

        List<Post> postsUser3 = new ArrayList<>();
        postsUser3.add(new Post("1","IMO that is enough", new Date()));
        postsUser3.add(new Post("2","LOL crazy thing", new Date()));
        postsUser3.add(new Post("3","A wild post appears!", new Date()));

        posts.put("3", postsUser3);
    }

    public List<Post> findAllBy(String userId) {
        return posts.get(userId);
    }

    public Optional<Post> findBy(String userId, String postId) {
        for (Post p: posts.get(userId)) {
            if (p.getId().equals(postId)) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

    public List<UserPost> findAll() {
        List<UserPost> allPosts = new ArrayList<>();

        posts.forEach((userId, userPosts) -> allPosts.add(
                new UserPost(userDao.findBy(userId).get(), userPosts))
        );
        return allPosts;
    }

    public Optional<Post> save(String userId, Post post) {
        List<Post> userPosts = posts.get(userId);

        if (userPosts == null) {
            userPosts = new ArrayList();
        }

        post.setId(String.valueOf(userPosts.size() + 1));
        userPosts.add(post);
        posts.put(userId, userPosts);

        return Optional.of(post);
    }

}
