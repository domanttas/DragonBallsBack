package com.dragonballs.controllers;

import com.dragonballs.entities.User;
import com.dragonballs.exceptions.UserExistsException;
import com.dragonballs.exceptions.UserPasswordNotValidException;
import com.dragonballs.exceptions.UsernameNotValidException;
import com.dragonballs.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserService userService;

 /*   @PostMapping(value = "/api/user/register")
    public User registerUser(@RequestBody User user) {
        try {
            return userService.registerUser(user);
        } catch (UserExistsException userExistsException) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, userExistsException.getMessage());
        } catch (UsernameNotValidException usernameNotValidException) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, usernameNotValidException.getMessage());
        } catch (UserPasswordNotValidException userPasswordNotValidException) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, userPasswordNotValidException.getMessage());
        }
    }*/


    @PostMapping(value = "/api/user")
    public ResponseEntity<Object> registerUser(@RequestBody User user) throws UsernameNotValidException, UserPasswordNotValidException, UserExistsException {
        User savedUser = userService.registerUser(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "/api/user")
    public List<User> getUsers() {return this.userService.getUsers();}
}
