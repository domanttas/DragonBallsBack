package com.dragonballs.controllers;

import com.dragonballs.entities.User;
import com.dragonballs.responsedatamapping.UserLocationCreator;
import com.dragonballs.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserLocationCreator userLocationCreator;

    @PostMapping(value = "/api/user")
    public ResponseEntity<Object> registerUser(@RequestBody User user) {
        User savedUser = userService.registerUser(user);
        URI location = userLocationCreator.userLocationCreator(savedUser);

        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "/api/user")
    public List<User> getUsers() {
        return userService.getUsers();
    }
}
