package org.expensetracker.expensetrackerapi.exception;

import java.io.Serial;

public class ExpenseNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public ExpenseNotFoundException(String message) {
        super(message);
    }

    public ExpenseNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
