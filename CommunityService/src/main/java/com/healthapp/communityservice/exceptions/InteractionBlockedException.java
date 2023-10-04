package com.healthapp.communityservice.exceptions;

import org.springframework.http.HttpStatus;

public class InteractionBlockedException extends PostManipulationException{
    public InteractionBlockedException(String message) {
        super("InteractionBlockedException", "Interacting in a post or connection", message, HttpStatus.FORBIDDEN);
    }
}
