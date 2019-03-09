package com.dragonballs.services.user;

import com.dragonballs.entities.User;
import com.dragonballs.exceptions.UserValidationException;

import java.util.regex.Pattern;

public class UserValidator {

    private static final String USERNAME_BOTTOM_MARGIN = "Username must be longer than 5 symbols";
    private static final int USERNAME_BOTTOM_MARGIN_VALUE = 5;

    private static final String PASSWORD_MARGIN = "Password must be between 7 and 12";
    private static final String PASSWORD_REGEX = "^[a-zA-Z0-9]*$";
    private static final String PASSWORD_MUST_BE_ALPHANUMERIC = "Password must be alphanumeric";
    private static final int PASSWORD_BOTTOM_MARGIN_VALUE = 7;
    private static final int PASSWORD_UPPER_MARGIN_VALUE = 12;

    private static final String EMAIL_REGEX = "\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b";
    private static final String EMAIL_IS_NOT_VALID = "User email is not valid";

    public void validate(User user)
            throws RuntimeException {

        if (user.getUsername().length() < USERNAME_BOTTOM_MARGIN_VALUE) {
            throwUserValidationException(USERNAME_BOTTOM_MARGIN);
        }

        if (user.getPasswordHash().length() < PASSWORD_BOTTOM_MARGIN_VALUE || user.getPasswordHash().length() > PASSWORD_UPPER_MARGIN_VALUE) {
            throwUserValidationException(PASSWORD_MARGIN);
        }

        if (!Pattern.matches(PASSWORD_REGEX, user.getPasswordHash())) {
            throwUserValidationException(PASSWORD_MUST_BE_ALPHANUMERIC);
        }

        if (!Pattern.matches(EMAIL_REGEX, user.getEmail())) {
            throwUserValidationException(EMAIL_IS_NOT_VALID);
        }
    }

    private void throwUserValidationException(String message) {
        throw new UserValidationException(message);
    }
}
