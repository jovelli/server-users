package com.rest.services.controllers;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.transaction.Transactional;
import javax.validation.Valid;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.rest.services.beans.User;
import com.rest.services.dao.PostRepository;
import com.rest.services.dao.UserRepository;
import com.rest.services.exceptions.UserNotFoundException;
import org.hibernate.SessionFactory;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.hateoas.Resource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.hateoas.Link;
import org.springframework.http.converter.json.MappingJacksonValue;
import com.fasterxml.jackson.databind.ser.FilterProvider;

@RestController
@Transactional
public class UsersController {

    private static final String[] FILTER_ALL_USERS = {"name", "birthDate"};
    private static final String[] FILTER_USER_V1 = {"id", "name", "nationalId"};;
    private static final String[] FILTER_USER_V2 = {"id", "name"};

    @Autowired
    private UserRepository userRepository;

    @Autowired
//    private EntityManagerFactory emf = EntityManagerFactory.
    private EntityManager entityManager ;


    //private SessionFactory sessionFactory = entityManager.;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MessageSource messageSource;

    @GetMapping(path="/users-bean")
    public User user() {
        return new User(1, "Kiwi", new Date(), "4321432123");
    }

    @GetMapping(path="/users-bean/name/{name}")
    public User userName(@PathVariable String name) {
        return new User(1, name, new Date(), "6543654323");
    }

    @GetMapping(path="/users")
    public MappingJacksonValue retrieveAll() {
        List<User> users = userRepository.findAll();
        if (users.size() == 0) throw new UserNotFoundException("No Users");

        MappingJacksonValue mapping = new MappingJacksonValue(users);
        mapping.setFilters(usersFilter(FILTER_ALL_USERS));

        return mapping;
    }

    @GetMapping("/v1/users/{userId}")
    public MappingJacksonValue retrieveUserV1(@PathVariable long userId) {
        return retrieveUser(userId, usersFilter(FILTER_USER_V1));
    }

    @GetMapping("/v2/users/{userId}")
    public MappingJacksonValue retrieveUserV2(@PathVariable long userId) {
        return retrieveUser(userId, usersFilter(FILTER_USER_V2));
    }

    private MappingJacksonValue retrieveUser(long userId, FilterProvider filters) {
        Resource<User> resource = new Resource(userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User doesn't exist")));

        Link linkTo = linkTo(methodOn(this.getClass()).retrieveAll()).withRel("all-users");
        resource.add(linkTo);

        MappingJacksonValue mapping = new MappingJacksonValue(resource);
        mapping.setFilters(filters);

        return mapping;
    }

    private FilterProvider usersFilter(String[] fields) {
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(fields);
        return new SimpleFilterProvider().addFilter("UsersFilter", filter);
    }

    @PostMapping(path="/users")
    public ResponseEntity create(@Valid @RequestBody User user) {

        entityManager.persist(user);
        entityManager.close();

        return Optional.of(userRepository.save(user)).map(userSaved ->
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
    public ResponseEntity deleteUser(@PathVariable long userId) {
        userRepository.deleteById(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/hello-world-internationalized")
    public String helloWorldInternationalized(
            @RequestHeader(name="Accept-Language", required=false) Locale locale) {
        return messageSource.getMessage("good.morning.message", null, locale);
    }

}
