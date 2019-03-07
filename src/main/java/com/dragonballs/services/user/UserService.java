package com.dragonballs.services.user;

import com.dragonballs.dao.UserDAO;
import com.dragonballs.entities.User;
import com.dragonballs.exceptions.UserValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserValidator userValidator;


    public User registerUser(User user) {
        userValidator.validate(user);

        User existingUser = userDAO.findByEmail(user.getEmail());

        if (existingUser != null) {
            throw new UserValidationException("User already exists");
        }
        user.setPasswordHash(bCryptPasswordEncoder.encode(user.getPasswordHash()));

        return userDAO.registerUser(user);

    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        for (User user : userDAO.getAllUsers()) {
            users.add(user);
        }
        return users;
    }

}
