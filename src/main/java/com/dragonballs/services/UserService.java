package com.dragonballs.services;

import com.dragonballs.entities.User;
import com.dragonballs.exceptions.UserExistsException;

public interface UserService {
    User registerUser(User user) throws UserExistsException;
}
