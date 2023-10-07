package com.healthapp.userservice.exception;

public class PasswordException extends RuntimeException {
    private static String MESSAGE="Password must be at least 5 characters long.";
    public PasswordException() {
        super(MESSAGE);
    }
}

