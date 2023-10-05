package com.healthapp.communityservice.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidUnfollowException extends ConnectionException {
    public InvalidUnfollowException(String message){
        super("InvalidUnfollowException", "Unfollow an user", message, HttpStatus.BAD_REQUEST);
    }
}
