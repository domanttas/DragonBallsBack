package com.dragonballs.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Password must be between 7 and 12 characters and be alphanumeric")
public class UserPasswordNotValidException extends RuntimeException {
    public UserPasswordNotValidException(String message) {
        super(message);
    }
}
