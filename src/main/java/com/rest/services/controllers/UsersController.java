package com.rest.services.controllers;

import java.util.Date;
import java.util.List;
import com.rest.services.beans.User;
import com.rest.services.dao.UserDao;
import com.rest.services.exceptions.UserNotFoundException;
import com.rest.services.exceptions.InvalidUserException;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UsersController {

    @Autowired
    private UserDao userDao;

    @GetMapping(path="/users-bean")
    public User userBean() {
        return new User("1", "Kiwi", new Date());
    }

    @GetMapping(path="/users-bean/name/{name}")
    public User userBeanName(@PathVariable String name) {
        return new User("1", name, new Date());
    }


    @GetMapping(path="/users")
    public List<User> retrieveAll() {
        List<User> users = userDao.findAll();
        if (users.size() == 0) throw new UserNotFoundException("No Users");

        return users;
    }

    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable String id)
    {
        return userDao.findBy(id).orElseThrow(() -> new UserNotFoundException("User doesn't exist"));
    }

    @PostMapping(path="/users")
    public ResponseEntity create(@RequestBody User user) {
        if (user.getName().equals("")) throw new InvalidUserException("Invalid user Name");

        return userDao.save(user).map(userSaved ->
            ResponseEntity.created(
                ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(userSaved.getId())
                    .toUri()
            ).build()
        ).orElse(
           ResponseEntity.badRequest().build()
        );
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity deleteUser(@PathVariable String userId) {
        return userDao.deleteBy(userId).map(userDeleted ->
            ResponseEntity.noContent().build()
        ).orElseThrow(
            () -> new UserNotFoundException("User doesn't exist")
        );
    }


}
