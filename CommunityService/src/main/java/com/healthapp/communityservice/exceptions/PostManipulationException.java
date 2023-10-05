package com.healthapp.communityservice.exceptions;

import org.springframework.http.HttpStatus;

public class PostManipulationException extends CustomException{
    public PostManipulationException(String exceptionName, String operation, String message, HttpStatus httpStatus) {
        super(exceptionName, "Post manipulation", operation, message, httpStatus);
    }
}
