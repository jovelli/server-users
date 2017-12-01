package com.rest.services.exceptions;

public class UserNotValidException extends RuntimeException {
    public UserNotValidException(String message) {
        super(message);
    }
}
