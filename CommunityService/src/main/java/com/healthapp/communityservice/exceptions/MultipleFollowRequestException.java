package com.healthapp.communityservice.exceptions;

import org.springframework.http.HttpStatus;

public class MultipleFollowRequestException extends ConnectionException{
    public MultipleFollowRequestException(String message) {
        super("MultipleFollowAttemptException", "Follow an user", message, HttpStatus.BAD_REQUEST);
    }
}
