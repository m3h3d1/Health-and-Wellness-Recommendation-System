package com.healthapp.notificationservice.exceptions;

import jakarta.persistence.PersistenceException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

// This class serves as a global exception handler for handling various types of exceptions
// that may occur during API operations.
@ControllerAdvice
public class GlobalExceptionHandler {

    // Exception handler for handling NullPointerException, DataAccessException, and PersistenceException.
    @ExceptionHandler({NullPointerException.class, DataAccessException.class, PersistenceException.class})
    public ResponseEntity<ErrorResponse> handleDatabaseExceptions(Exception e, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getClass().getSimpleName(),
                "Database Operation",
                "Database Error",
                "An error occurred while performing a database operation",
                HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                new Date(),
                request.getRequestURI()
        );

        // Return an HTTP response with a status code indicating an internal server error.
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    // Exception handler for handling IllegalArgumentException.
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(Exception e, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getClass().getSimpleName(),
                "Invalid Argument",
                "Bad Request",
                "Invalid argument provided",
                HttpStatus.BAD_REQUEST.toString(),
                new Date(),
                request.getRequestURI()
        );

        // Return an HTTP response with a status code indicating a bad request.
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
