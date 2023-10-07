package com.healthapp.recommendationserviceauto.exception;

public class EmptyResultException extends RuntimeException {
    private static final String MESSAGE = "No health data found!";

    public EmptyResultException() {
        super(MESSAGE);
    }
}
