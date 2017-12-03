package com.rest.services.beans;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

public class Post {
    private String id;

    @Size(min=2, message="Message must have at least 2 characters")
    private String message;

    @Past(message="Created date must be in the past")
    @NotNull(message="Created date must not be empty")
    private Date createdDate;

    public Post(String id, String message, Date createdDate) {
        this.id = id;
        this.message = message;
        this.createdDate = createdDate;
    }

    public Post() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public String getMessage() {
        return message;
    }
}
