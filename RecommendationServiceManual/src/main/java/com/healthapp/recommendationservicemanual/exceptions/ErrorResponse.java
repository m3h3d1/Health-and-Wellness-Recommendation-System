package com.healthapp.recommendationservicemanual.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

// Class representing an ErrorResponse
@Getter @Setter @AllArgsConstructor
public class ErrorResponse {
    private String exception;    // The type of exception or error
    private String operation;    // The operation or action that triggered the error
    private String type;         // The type or category of the error
    private String message;      // A descriptive message explaining the error
    private String status;       // The HTTP status code associated with the error
    private Date timeStamp;      // The timestamp when the error occurred
    private String apiPath;      // The API endpoint or path where the error occurred
}
