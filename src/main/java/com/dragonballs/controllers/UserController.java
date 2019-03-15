package com.dragonballs.controllers;

import com.dragonballs.entities.User;
import com.dragonballs.services.authentication.JwtAuthenticationResponse;
import com.dragonballs.services.authentication.JwtTokenUtil;
import com.dragonballs.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@RestController
public class UserController {
    private int AUTH_TOKEN_SUBSTRING_VALUE = 7;

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

    @GetMapping(value = "/api/user/refresh")
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String authToken = request.getHeader("Authorization");

        String token = authToken.substring(AUTH_TOKEN_SUBSTRING_VALUE);
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
    public ResponseEntity<User> getAuthenticatedUserByToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(AUTH_TOKEN_SUBSTRING_VALUE);
        String username = jwtTokenUtil.getUsernameFromToken(token);

        return ResponseEntity.ok().body(userService.getUserByUsername(username));
    }
}
