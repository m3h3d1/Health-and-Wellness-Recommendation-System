package com.healthapp.userservice.exception;

public class UnmatchedPasswordException extends RuntimeException{
    private static String MESSAGE = "The old password does not match.";
    public UnmatchedPasswordException() {
        super(MESSAGE);
    }
}
