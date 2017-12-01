package com.rest.services.beans;

import java.util.Date;

public class Post {
    private String id;
    private String message;
    private Date created;

    public Post(String id, String message, Date created) {
        this.id = id;
        this.message = message;
        this.created = created;
    }

    public String getId() {
        return id;
    }

    public Date getCreated() {
        return created;
    }

    public String getMessage() {
        return message;
    }
}
