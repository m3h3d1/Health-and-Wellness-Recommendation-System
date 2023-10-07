package com.healthapp.userservice.exception;

public class UnauthorizedUserException extends RuntimeException {
    private static final String MESSAGE = "You are not authorized to use this Api.";
    public UnauthorizedUserException() {
        super(MESSAGE);
    }
}

