package com.dragonballs.services.user;

import com.dragonballs.dao.UserDAO;
import com.dragonballs.entities.User;
import com.dragonballs.exceptions.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private UserValidator userValidator;

    public UserService() {
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
        userValidator = new UserValidator();
    }

    public User registerUser(User user) {
        userValidator.validate(user);

        User existingUser = userDAO.findByEmail(user.getEmail());

        if (existingUser != null) {
            throw new UserException("User already exists");
        } else {
            user.setPasswordHash(bCryptPasswordEncoder.encode(user.getPasswordHash()));

            return userDAO.registerUser(user);
        }
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        for (User user : userDAO.getAllUsers()) {
            users.add(user);
        }
        return users;
    }

    public User createSessionToken(User user) {
        User existingUser = userDAO.findByUsername(user.getUsername());

        if (existingUser == null) {
            throw new UserException("User does not exist");
        }

        if (!bCryptPasswordEncoder.matches(user.getPasswordHash(), existingUser.getPasswordHash())) {
            throw new UserException("Password is incorrect");
        }

        // Is it okay to ignore NoSuchAlgorithmException???
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(user.getEmail().getBytes());
            user.setSessionToken(new String(messageDigest.digest()));
        } catch (NoSuchAlgorithmException ex) {
            throw new UserException("Something went wrong with authentication");
        }

        return user;
    }

    public boolean isUserLoggedIn(String userSessionToken) {
        User loggedInUser = userDAO.findBySessionToken(userSessionToken);

        if (loggedInUser == null) {
            return false;
        }

        return true;
    }

}
