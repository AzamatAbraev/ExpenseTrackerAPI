package org.expensetracker.expensetrackerapi.exception;

public class CustomUnauthorizedException extends RuntimeException {
    public CustomUnauthorizedException(String message) {
        super(message);
    }

    public CustomUnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}
