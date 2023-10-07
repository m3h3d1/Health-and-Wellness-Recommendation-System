package com.healthapp.recommendationservicemanual.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

/**
 * GlobalExceptionHandler handles exceptions thrown by controllers and provides consistent error responses.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles EntityNotFoundException and returns a corresponding error response.
     *
     * @param ex The EntityNotFoundException that was thrown.
     * @param request The current WebRequest.
     * @return A ResponseEntity containing an error response.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getClass().getSimpleName(),
                "Operation failed",
                "EntityNotFound",
                ex.getMessage(),
                HttpStatus.NOT_FOUND.toString(),
                new Date(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles ValidationException and returns a corresponding error response.
     *
     * @param ex The ValidationException that was thrown.
     * @param request The current WebRequest.
     * @return A ResponseEntity containing an error response.
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(ValidationException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getClass().getSimpleName(),
                "Validation failed",
                "Validation",
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.toString(),
                new Date(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles InternalServerErrorException and returns a corresponding error response.
     *
     * @param ex The InternalServerErrorException that was thrown.
     * @param request The current WebRequest.
     * @return A ResponseEntity containing an error response.
     */
    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ErrorResponse> handleInternalServerErrorException(InternalServerErrorException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getClass().getSimpleName(),
                "Internal Server Error",
                "InternalServerError",
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                new Date(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles DuplicateEntityException and returns a corresponding error response.
     *
     * @param ex DuplicateEntityException that was thrown.
     * @param request The current WebRequest.
     * @return A ResponseEntity containing an error response.
     */
    @ExceptionHandler(DuplicateEntityException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateEntityException(DuplicateEntityException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getClass().getSimpleName(),
                "Duplicate Entity",
                "Creation",
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.toString(),
                new Date(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles other exceptions and returns a generic error response.
     *
     * @param ex The Exception that was thrown.
     * @param request The current WebRequest.
     * @return A ResponseEntity containing an error response.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleOtherExceptions(Exception ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getClass().getSimpleName(),
                "Operation failed",
                "OtherError",
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                new Date(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
