package com.dragonballs.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Username must be longer than 5 symbols")
public class UsernameNotValidException extends RuntimeException {
    public UsernameNotValidException() {
    }
}
