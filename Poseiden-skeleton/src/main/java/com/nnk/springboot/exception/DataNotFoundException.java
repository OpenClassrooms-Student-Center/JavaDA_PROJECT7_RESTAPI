package com.nnk.springboot.exception;

public class DataNotFoundException extends Exception {

    public DataNotFoundException(String message){
        super(message);
    }
}
