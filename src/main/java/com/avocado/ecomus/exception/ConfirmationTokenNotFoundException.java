package com.avocado.ecomus.exception;

public class ConfirmationTokenNotFoundException extends RuntimeException {
    public ConfirmationTokenNotFoundException(String message) {
        super(message);
    }
}
