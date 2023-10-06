package com.healthapp.mentalhealthservice.exception;

public class DataNotFindByUserIdException extends RuntimeException {
    public DataNotFindByUserIdException(String message) {
        super(message);
    }
}
