package com.healthapp.userservice.exception;

public class ContactUpdateException extends RuntimeException {
    private static String MESSAGE = "Failed to update contact information.";
    public ContactUpdateException() {
        super(MESSAGE);
    }
}

