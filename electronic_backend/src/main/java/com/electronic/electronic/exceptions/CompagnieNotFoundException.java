package com.electronic.electronic.exceptions;

public class CompagnieNotFoundException extends RuntimeException {
    public CompagnieNotFoundException(String message) {
        super(message);
    }
}