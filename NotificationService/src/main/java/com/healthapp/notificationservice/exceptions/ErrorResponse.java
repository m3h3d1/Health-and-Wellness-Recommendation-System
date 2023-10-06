package com.healthapp.notificationservice.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

// This class represents an error response object that can be used to provide detailed information
// about errors that occur during API operations.
@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {
    private String exception;
    private String operation;
    private String type;
    private String message;
    private String status;
    private Date timeStamp;
    private String apiPath;
}
