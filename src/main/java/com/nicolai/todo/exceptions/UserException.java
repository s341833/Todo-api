package com.nicolai.todo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UserException extends RuntimeException {
    public UserException(String message) {
        super(message);
    }
}
