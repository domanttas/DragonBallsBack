package com.dragonballs.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class DeedException extends RuntimeException {

    public DeedException(String message) {
        super(message);
    }
}
