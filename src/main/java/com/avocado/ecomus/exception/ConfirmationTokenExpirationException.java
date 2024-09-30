package com.avocado.ecomus.exception;

public class ConfirmationTokenExpirationException extends RuntimeException {
    public ConfirmationTokenExpirationException(String message) {
        super(message);
    }
}
