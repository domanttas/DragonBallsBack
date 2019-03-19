package com.dragonballs.controllers;

import com.dragonballs.entities.User;
import com.dragonballs.services.authentication.JwtAuthenticationResponse;
import com.dragonballs.services.authentication.JwtTokenUtil;
import com.dragonballs.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/user")
public class UserController {
    private int AUTH_TOKEN_SUBSTRING_VALUE = 7;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping(value = "/create")
    public ResponseEntity registerUser(@RequestBody User user) {
        userService.registerUser(user);

        return ResponseEntity.ok().body(null);
    }

    @PostMapping(value = "/auth")
    public ResponseEntity createAuthenticationToken(@RequestBody User user) {
        userService.validateUser(user);

        String token = jwtTokenUtil.generateToken(user);

        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }

    @GetMapping(value = "/refresh")
    public ResponseEntity refreshAndGetAuthenticationToken(HttpServletRequest request) {
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

    @GetMapping
    public ResponseEntity<User> getAuthenticatedUserByToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(AUTH_TOKEN_SUBSTRING_VALUE);
        String username = jwtTokenUtil.getUsernameFromToken(token);

        return ResponseEntity.ok().body(userService.getUserByUsername(username));
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        User fetchedUser = userService.getUserByUsername(username);

        return ResponseEntity.ok().body(fetchedUser);
    }
}
