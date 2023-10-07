package com.healthapp.notificationservice.exceptions;

public class ExternalCallForbiddenException extends RuntimeException{
    public ExternalCallForbiddenException(){
        super("Secret token is invalid, external call prohibited!");
    }
}
