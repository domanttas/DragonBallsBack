package com.dragonballs.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class DeedException extends RuntimeException {

    private List<String> missingUsernames = new ArrayList<>();

    public DeedException(String message) {
        super(message);
    }

    public DeedException(String message, List<String> missingUsernames) {
        super(message);
        this.missingUsernames = missingUsernames;
    }
}
