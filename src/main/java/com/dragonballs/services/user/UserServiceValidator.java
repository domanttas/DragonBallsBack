package com.dragonballs.services.user;

import com.dragonballs.entities.User;
import com.dragonballs.exceptions.UserEmailNotValidException;
import com.dragonballs.exceptions.UserPasswordNotValidException;
import com.dragonballs.exceptions.UsernameNotValidException;

import java.util.regex.Pattern;

public class UserServiceValidator {

    public boolean areInputFieldsValid(User user)
            throws UsernameNotValidException, UserPasswordNotValidException, UserEmailNotValidException {

        if (user.getUsername().length() < 5) {
            throw new UsernameNotValidException();
        } else if (user.getPasswordHash().length() < 7 || user.getPasswordHash().length() > 12) {
            throw new UserPasswordNotValidException();
        } else if (!Pattern.matches("^[a-zA-Z0-9]*$", user.getPasswordHash())) {
            throw new UserPasswordNotValidException();
        } else if (!Pattern.matches("\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b", user.getEmail())) {
            throw new UserEmailNotValidException();
        }

        return true;
    }
}
