package com.propertymanagement.PropertyManagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PMExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> theHandler(DataNotFoundException exc) {
        ErrorResponse error = new ErrorResponse();
        error.setTimestamp(System.currentTimeMillis());
        error.setMessage(exc.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // catch all

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerException(Exception exc) {
        ErrorResponse error = new ErrorResponse();
        error.setTimestamp(System.currentTimeMillis());
        error.setMessage(exc.getMessage());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
