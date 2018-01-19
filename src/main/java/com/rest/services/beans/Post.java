package com.rest.services.beans;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "POSTS")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min=2, message="Message must have at least 2 characters")
    private String message;

    @Past(message="Created date must be in the past")
    @NotNull(message="Created date must not be empty")
    private Date createdDate;

    @ManyToOne(fetch=FetchType.LAZY)
    @JsonIgnore
    private User user;

    public Post(Long id, String message, Date createdDate) {
        this.id = id;
        this.message = message;
        this.createdDate = createdDate;
    }

    public Post() {}

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
