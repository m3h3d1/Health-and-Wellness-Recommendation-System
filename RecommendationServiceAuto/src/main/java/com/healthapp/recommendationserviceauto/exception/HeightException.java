package com.healthapp.recommendationserviceauto.exception;

public class HeightException extends RuntimeException {
    public static final String LOW_HEIGHT_MESSAGE = "Low height detected. Consult a healthcare provider.";
    public static final String HIGH_HEIGHT_MESSAGE = "High height detected. Consider a healthcare provider's advice.";

    public HeightException(String message) {
        super(message);
    }
}

