package com.healthapp.userservice.exception;

public class ProfileUpdateException extends RuntimeException {
    private static String MESSAGE ="Failed to update profile information.";
    public ProfileUpdateException() {
        super(MESSAGE);
    }
}

