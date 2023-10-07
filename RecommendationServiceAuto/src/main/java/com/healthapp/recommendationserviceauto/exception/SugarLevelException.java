package com.healthapp.recommendationserviceauto.exception;

public class SugarLevelException extends RuntimeException {
    public static final String HIGH_SUGAR_MESSAGE = "High sugar level detected.";
    public static final String LOW_SUGAR_MESSAGE = "Low sugar level detected.";

    public SugarLevelException(String message) {
        super(message);
    }
}

