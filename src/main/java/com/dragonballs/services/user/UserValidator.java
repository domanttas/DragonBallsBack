package com.dragonballs.services.user;

import com.dragonballs.entities.User;
import com.dragonballs.exceptions.UserException;

import java.util.regex.Pattern;

public class UserValidator {

    private static final String USERNAME_BOTTOM_MARGIN = "Username must be longer than 5 symbols";
    private static final int USERNAME_BOTTOM_MARGIN_VALUE = 5;

    private static final String PASSWORD_MARGIN = "Password must be between 7 and 12";
    private static final String PASSWORD_REGEX = "^[a-zA-Z0-9]*$";
    private static final String PASSWORD_MUST_BE_ALPHANUMERIC = "Password must be alphanumeric";
    private static final int PASSWORD_BOTTOM_MARGIN_VALUE = 7;
    private static final int PASSWORD_UPPER_MARGIN = 12;

    private static final String EMAIL_REGEX = "\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b";
    private static final String EMAIL_IS_NOT_VALID = "User email is not valid";

    public boolean validate(User user)
            throws UserException {

        if (user.getUsername().length() < USERNAME_BOTTOM_MARGIN_VALUE) {
            throw new UserException(USERNAME_BOTTOM_MARGIN);
        } else if (user.getPasswordHash().length() < PASSWORD_BOTTOM_MARGIN_VALUE || user.getPasswordHash().length() > PASSWORD_UPPER_MARGIN) {
            throw new UserException(PASSWORD_MARGIN);
        } else if (!Pattern.matches(PASSWORD_REGEX, user.getPasswordHash())) {
            throw new UserException(PASSWORD_MUST_BE_ALPHANUMERIC);
        } else if (!Pattern.matches(EMAIL_REGEX, user.getEmail())) {
            throw new UserException(EMAIL_IS_NOT_VALID);
        }

        return true;
    }
}
