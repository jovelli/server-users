package com.rest.services.controllers;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.validation.Valid;
import com.rest.services.beans.User;
import com.rest.services.dao.UserDao;
import com.rest.services.exceptions.UserNotFoundException;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.hateoas.Link;

@RestController
public class UsersController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private MessageSource messageSource;

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
    public Resource<User> retrieveUser(@PathVariable String id)
    {
        Resource<User> resource = new Resource(userDao.findBy(id).orElseThrow(() -> new UserNotFoundException("User doesn't exist")));

        Link linkTo = linkTo(methodOn(this.getClass()).retrieveAll()).withRel("all-users");
        resource.add(linkTo);

        return resource;
    }

    @PostMapping(path="/users")
    public ResponseEntity create(@Valid @RequestBody User user) {
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

    @GetMapping(path = "/hello-world-internationalized")
    public String helloWorldInternationalized(
            @RequestHeader(name="Accept-Language", required=false) Locale locale) {
        return messageSource.getMessage("good.morning.message", null, locale);
    }

}
