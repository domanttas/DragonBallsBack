package com.dragonballs.services.user;

import com.dragonballs.entities.User;
import com.dragonballs.exceptions.UserException;

import java.util.regex.Pattern;

public class UserServiceValidator {

    public boolean areInputFieldsValid(User user)
            throws UserException {

        if (user.getUsername().length() < 5) {
            throw new UserException("Username must be longer than 5 symbols");
        } else if (user.getPasswordHash().length() < 7 || user.getPasswordHash().length() > 12) {
            throw new UserException("Password must be between 7 and 12 characters and be alphanumeric");
        } else if (!Pattern.matches("^[a-zA-Z0-9]*$", user.getPasswordHash())) {
            throw new UserException("Password must be between 7 and 12 characters and be alphanumeric");
        } else if (!Pattern.matches("\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b", user.getEmail())) {
            throw new UserException("User email is not valid");
        }

        return true;
    }
}
