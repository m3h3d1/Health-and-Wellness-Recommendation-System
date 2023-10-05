package com.healthapp.communityservice.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends CustomException{
    public NotFoundException(String exceptionName, String operation, String message) {
        super(exceptionName, "Not found", operation, message, HttpStatus.NOT_FOUND);
    }
}
