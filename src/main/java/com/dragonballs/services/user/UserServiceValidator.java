package com.dragonballs.services.user;

import com.dragonballs.entities.User;
import com.dragonballs.exceptions.UserPasswordNotValidException;
import com.dragonballs.exceptions.UsernameNotValidException;

import java.util.regex.Pattern;

public class UserServiceValidator {

    public boolean areInputFieldsValid(User user)
            throws UsernameNotValidException, UserPasswordNotValidException {

        if (user.getUsername().length() < 5) {
            throw new UsernameNotValidException();
        } else if (user.getPasswordHash().length() < 7 || user.getPasswordHash().length() > 12) {
            throw new UserPasswordNotValidException();
        } else if (!Pattern.matches("^[a-zA-Z0-9]*$", user.getPasswordHash())) {
            throw new UserPasswordNotValidException();
        }

        return true;
    }
}
