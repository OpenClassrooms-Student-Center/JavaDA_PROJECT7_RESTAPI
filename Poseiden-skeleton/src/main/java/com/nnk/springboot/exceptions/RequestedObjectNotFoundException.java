package com.nnk.springboot.exceptions;

public class RequestedObjectNotFoundException extends Exception {
    public RequestedObjectNotFoundException(String message) {
        super(message);
    }
}
