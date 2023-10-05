package com.healthapp.communityservice.exceptions;

import org.springframework.http.HttpStatus;

public class PostPrivacyException extends PostManipulationException{
    public PostPrivacyException(String message) {
        super("PostPrivacyException", "Changing privacy of a post", message, HttpStatus.NOT_ACCEPTABLE);
    }
}
