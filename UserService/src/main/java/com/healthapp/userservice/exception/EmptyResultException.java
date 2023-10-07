package com.healthapp.userservice.exception;

public class EmptyResultException extends RuntimeException {
    private static final String MESSAGE = "No data found";

    public EmptyResultException() {
        super(MESSAGE);
    }
}
