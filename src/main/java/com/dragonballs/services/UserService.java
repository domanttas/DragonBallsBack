package com.dragonballs.services;

import com.dragonballs.entities.User;
import com.dragonballs.exceptions.UserExistsException;
import com.dragonballs.exceptions.UserPasswordNotValidException;
import com.dragonballs.exceptions.UsernameNotValidException;

public interface UserService {
    User registerUser(User user) throws UserExistsException, UsernameNotValidException, UserPasswordNotValidException;
}
