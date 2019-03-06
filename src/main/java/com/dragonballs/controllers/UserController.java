package com.dragonballs.controllers;

import com.dragonballs.entities.User;
import com.dragonballs.services.user.JwtAuthenticationResponse;
import com.dragonballs.services.user.JwtTokenUtil;
import com.dragonballs.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping(value = "/api/user")
    public ResponseEntity<Object> registerUser(@RequestBody User user) {
        User savedUser = userService.registerUser(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping(value = "/api/user/auth")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody User user) {
        userService.validateUser(user);

        String token = jwtTokenUtil.generateToken(user);

        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }

    @GetMapping(value = "/api/user/all")
    public List<User> getUsers() {
        return this.userService.getUsers();
    }

    @GetMapping(value = "/api/user/refresh")
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String authToken = request.getHeader("authorization");

        String token = authToken.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);

        User user = userService.getUserByUsername(username);

        if (jwtTokenUtil.canTokenBeRefreshed(token)) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping(value = "/api/user")
    public User getAuthenticatedUserByToken(HttpServletRequest request) {
        String token = request.getHeader("authorization").substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);

        return userService.getUserByUsername(username);
    }

    //https://github.com/szerhusenBC/jwt-spring-security-demo/blob/master/src/main/java/org/zerhusen/config/WebSecurityConfig.java
}
