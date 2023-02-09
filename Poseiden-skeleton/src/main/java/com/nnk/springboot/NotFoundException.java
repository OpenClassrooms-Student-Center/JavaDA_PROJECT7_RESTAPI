package com.nnk.springboot;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * NotFoundException class is responsible for handling the HTTP status code of 404 when a specific resource is not found.
 * It is an extension of the RuntimeException class and it throws a message when the exception is raised.
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}