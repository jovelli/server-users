package com.rest.services.beans;

import java.util.List;

public class UserPost {
    private User user;
    private List<Post> posts;

    public UserPost(User user, List<Post> posts) {
        this.user = user;
        this.posts = posts;
    }

    public UserPost() {}

    public User getUser() {
        return user;
    }

    public List<Post> getPosts() {
        return posts;
    }
}
