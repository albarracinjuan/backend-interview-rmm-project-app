package com.ninjaone.backendinterviewproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ExistingResourceException.class)
    public ResponseEntity handleException(ExistingResourceException e) {

        return new ResponseEntity(e.getMessage(),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity handleException(ResourceNotFoundException e) {

        return new ResponseEntity(e.getMessage(),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity handleException(BadRequestException e) {

        return new ResponseEntity(e.getMessage(),
                HttpStatus.BAD_REQUEST);
    }
}
