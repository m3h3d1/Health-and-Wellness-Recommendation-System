package com.healthapp.communityservice.exceptions;

import org.springframework.http.HttpStatus;

public class ConnectionException extends CustomException{

    public ConnectionException(String exceptionName, String operation, String message, HttpStatus httpStatus) {
        super(exceptionName, "Connections", operation, message, httpStatus);
    }
}
