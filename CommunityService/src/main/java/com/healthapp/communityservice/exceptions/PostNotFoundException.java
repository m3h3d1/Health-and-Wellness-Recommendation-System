package com.healthapp.communityservice.exceptions;

public class PostNotFoundException extends NotFoundException{
    public PostNotFoundException(String message) {
        super("PostNotFoundException", "Fetching a post", message);
    }
}
