package com.dragonballs.dao;

import com.dragonballs.entities.User;
import com.dragonballs.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDAO {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
}
