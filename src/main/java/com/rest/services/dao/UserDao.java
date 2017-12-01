package com.rest.services.dao;

import org.springframework.stereotype.Component;
import com.rest.services.beans.User;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@Component
public class UserDao {

    private static List<User> users = new ArrayList<User>();

    static {
        users.add(new User("1", "John", new Date()));
        users.add(new User("2", "Mary", new Date()));
        users.add(new User("3", "Oswald", new Date()));
    }

    public List<User> findAll() {
        return users;
    }

    public Optional<User> findBy(String id) {
        for (User user: users) {
            if (user.getId().equals(id)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public Optional<User> save(User user) {
        user.setId(String.valueOf(users.size() + 1));
        users.add(user);
        return Optional.of(user);
    }
}

