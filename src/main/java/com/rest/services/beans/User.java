package com.rest.services.beans;

import io.swagger.annotations.ApiModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonFilter;

@ApiModel(description="User's definition")
@JsonFilter("UsersFilter")
@Entity
public class User {

    @Id
    @SequenceGenerator(name="user_sequence", sequenceName="USERSEQUENCE", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="user_sequence")
    private long id;

    @Size(min=2, message="Name must have at least 2 characters")
    @ApiModelProperty(notes="Name must have at least 2 characters")
    private String name;

    @Past(message="BirthDate must be in the past")
    @NotNull(message="BirthDate must not be empty")
    @ApiModelProperty(notes="BirthDate must not be empty")
    private Date birthDate;

    private String nationalId;

    @OneToMany(mappedBy="user", orphanRemoval=true)
    private List<Post> posts;

    public User(String name, Date birthDate, String nationalId) {
        this.name = name;
        this.birthDate = birthDate;
        this.nationalId = nationalId;
    }

    public User(long id, String name, Date birthDate, String nationalId) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.nationalId = nationalId;
    }

    public User() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getName() {
        return name;
    }

    public String getNationalId() {
        return nationalId;
    }

    public List<Post> getPosts() { return posts; }

    public void setPosts(List<Post> posts) { this.posts = posts; }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", nationalId=" + nationalId +
                '}';
    }
}
