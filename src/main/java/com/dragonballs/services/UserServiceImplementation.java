package com.dragonballs.services;

import com.dragonballs.entities.User;
import com.dragonballs.exceptions.UserExistsException;
import com.dragonballs.exceptions.UserPasswordNotValidException;
import com.dragonballs.exceptions.UsernameNotValidException;
import com.dragonballs.repositories.UserRepository;
import com.dragonballs.utils.UserServiceUtil;
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
    public User registerUser(User user)
            throws UserExistsException, UsernameNotValidException, UserPasswordNotValidException {
        User existingUser = userRepository.findByEmail(user.getEmail());

        try {
            UserServiceUtil.areUserRegistrationFieldsValid(user);
        } catch (UsernameNotValidException usernameNotValidException) {
            throw new UsernameNotValidException(usernameNotValidException.getMessage());
        } catch (UserPasswordNotValidException userPasswordNotValidException) {
            throw new UserPasswordNotValidException(userPasswordNotValidException.getMessage());
        }

        if (existingUser != null) {
            throw new UserExistsException("User already exists");
        } else {
            user.setPasswordHash(bCryptPasswordEncoder.encode(user.getPasswordHash()));

            return userRepository.save(user);
        }
    }
}
