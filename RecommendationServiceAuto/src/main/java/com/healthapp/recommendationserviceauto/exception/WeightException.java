package com.healthapp.recommendationserviceauto.exception;

public class WeightException extends RuntimeException {
    public static final String LOW_WEIGHT_MESSAGE = "Low weight detected. Consult a healthcare provider.";
    public static final String HIGH_WEIGHT_MESSAGE = "High weight detected. Consider a weight management plan.";

    public WeightException(String message) {
        super(message);
    }
}

