package com.ninjaone.backendinterviewproject.exceptions;

public class ExistingResourceException extends RuntimeException {

    public ExistingResourceException(String message) {
        super(message);
    }
}
