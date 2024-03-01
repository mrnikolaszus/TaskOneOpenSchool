package org.openschool.exception;

public class ValidationApiException extends RuntimeException {
    public ValidationApiException(String message) {
        super(message);
    }
}