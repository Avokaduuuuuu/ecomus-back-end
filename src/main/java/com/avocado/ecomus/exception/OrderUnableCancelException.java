package com.avocado.ecomus.exception;

public class OrderUnableCancelException extends RuntimeException {
    public OrderUnableCancelException(String message) {
        super(message);
    }
}
