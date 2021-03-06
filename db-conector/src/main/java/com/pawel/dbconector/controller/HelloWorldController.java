package com.pawel.dbconector.controller;

import com.pawel.dbconector.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class HelloWorldController {
    
    @Autowired
    private UserDataAccess userDataAccess;

    @GetMapping(path = "/user")
    public User getUser(@RequestParam(name = "identyfikator") long userId) {
        User user = userDataAccess.getUserById(userId);
        return user;
    }

    @GetMapping(path = "/users")
    public UserList getUsersByLogin(@RequestParam(name = "userLogin") String login) {
        UserList userList = new UserList();
        List<User> users = userDataAccess.getUsersByLogin(login);
        userList.setUsers(users);
        return userList;
    }

    @DeleteMapping(path = "/user")
    public void deleteUser(@RequestParam(name = "identyfikator") long userId) {
        userDataAccess.deleteUserById(userId);
    }

//    @PatchMapping(path = "/user")
//    public void updateUser(@RequestParam(name = "identyfikator") long userId, @RequestBody String password) {
//        userDataAccess.updateUserById(userId, password);
//    }

    @PatchMapping(path = "/user")
    public void updateUser(@RequestBody() User user) {
        userDataAccess.updateUserById(user);
    }

    @PostMapping(path = "/createUser")
    public String createUser(@RequestBody() User user) {
        userDataAccess.createUser(user);
        return user.getId().toString();
    }

    private User createsUsers() {
        RandomString gen = new RandomString(49, ThreadLocalRandom.current());
        String name = gen.nextString();

        User user = UserBuilder.anUser().withLogin(gen.nextString()).withPassword("1234").withFirstName(name).withLastName(name).build();
        userDataAccess.createUser(user);
        return user;
    }
}
