package com.healthapp.dataanalysisservice2.exception;

public class DataNotFindByUserIdException extends RuntimeException {
    public DataNotFindByUserIdException(String message) {
        super(message);
    }
}
