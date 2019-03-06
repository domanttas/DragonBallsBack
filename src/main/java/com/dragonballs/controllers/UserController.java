package com.dragonballs.controllers;

import com.dragonballs.entities.User;
import com.dragonballs.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "/api/user")
    public ResponseEntity<Object> registerUser(@RequestBody User user) {
        User savedUser = userService.registerUser(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping(value = "/api/user/auth")
    public String authenticateUser(@RequestBody User user) {
        User updatedUser = userService.createSessionToken(user);

        return updatedUser.getSessionToken();
    }

    @GetMapping(value = "/api/user/{userSessionToken}")
    public boolean isUserLoggedIn(@PathVariable String userSessionToken) {
        return userService.isUserLoggedIn(userSessionToken);
    }

    @GetMapping(value = "/api/user")
    public List<User> getUsers() {return this.userService.getUsers();}
}
