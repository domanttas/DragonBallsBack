package com.dragonballs.services;

import com.dragonballs.entities.User;
import com.dragonballs.exceptions.UserExistsException;
import com.dragonballs.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements UserService {
    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImplementation() {
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public User registerUser(User user) throws UserExistsException {
        User existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser != null) {
            throw new UserExistsException("User already exists");
        } else {
            user.setPasswordHash(bCryptPasswordEncoder.encode(user.getPasswordHash()));

            return userRepository.save(user);
        }
    }
}
