package com.healthapp.userservice.exception;

public class UserUpdateException extends RuntimeException {
    private static String MESSAGE = "Failed to update user information.";
    public UserUpdateException() {
        super(MESSAGE);
    }
}

