package com.nicolai.todo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class TodoException extends RuntimeException{
    public TodoException(String message) {
        super(message);
    }
}
