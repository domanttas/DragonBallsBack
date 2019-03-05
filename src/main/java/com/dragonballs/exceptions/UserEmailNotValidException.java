package com.dragonballs.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "User email is not valid")
public class UserEmailNotValidException extends RuntimeException {
    public UserEmailNotValidException(){
    }
}
