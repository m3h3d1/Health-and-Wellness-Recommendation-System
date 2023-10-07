package com.healthapp.recommendationserviceauto.exception;

public class BloodPressureException extends RuntimeException {
    public static final String HIGH_PRESSURE_MESSAGE = "High blood pressure is too high.";
    public static final String LOW_PRESSURE_MESSAGE = "Low blood pressure is too low.";

    public BloodPressureException(String message) {
        super(message);
    }
}
