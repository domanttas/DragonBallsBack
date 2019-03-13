package com.dragonballs.services.user;

import com.dragonballs.dao.UserDAO;
import com.dragonballs.entities.User;
import com.dragonballs.exceptions.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserValidator userValidator;

    public User registerUser(User user) {
        userValidator.validate(user);

        User existingUser = userDAO.findByEmail(user.getEmail());

        if (existingUser != null) {
            throw new UserException("User with this email already exists");
        }

        existingUser = getUserByUsername(user.getUsername());

        if (existingUser != null) {
            throw new UserException("User with this username already exists");
        }

        user.setPasswordHash(bCryptPasswordEncoder.encode(user.getPasswordHash()));
        return userDAO.registerUser(user);
    }

    //TODO: move to token wrapper

    public void validateUser(User user) {
        User existingUser = getUserByUsername(user.getUsername());

        if (existingUser == null) {
            throw new UserException("User does not exist");
        }

        if (!bCryptPasswordEncoder.matches(user.getPasswordHash(), existingUser.getPasswordHash())) {
            throw new UserException("Password is incorrect");
        }
    }

    public User getUserByUsername(String username) {
        return userDAO.findByUsername(username);
    }
}
