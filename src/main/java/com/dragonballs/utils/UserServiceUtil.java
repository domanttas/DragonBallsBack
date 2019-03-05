package com.dragonballs.utils;

import com.dragonballs.entities.User;
import com.dragonballs.exceptions.UserPasswordNotValidException;
import com.dragonballs.exceptions.UsernameNotValidException;

import java.util.regex.Pattern;

public class UserServiceUtil {
    public static void areUserRegistrationFieldsValid(User user)
            throws UsernameNotValidException, UserPasswordNotValidException {
        if (user.getUsername().length() < 5) {
            throw new UsernameNotValidException("Username must be longer than 5 symbols");
        } else if (user.getPasswordHash().length() < 7 || user.getPasswordHash().length() > 12) {
            throw new UserPasswordNotValidException("Password must be between 7 and 12");
        } else if (!Pattern.matches("^[a-zA-Z0-9]*$", user.getPasswordHash())) {
            throw new UserPasswordNotValidException("Password must be alphanumeric");
        }
    }
}
