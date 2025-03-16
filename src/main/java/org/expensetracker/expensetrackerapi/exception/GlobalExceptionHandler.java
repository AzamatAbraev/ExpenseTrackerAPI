package org.expensetracker.expensetrackerapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomNotFoundException.class)
    public ResponseEntity<ApiExceptionObject> handleExpenseNotFoundException(
            CustomNotFoundException ex, WebRequest request) {
        ApiExceptionObject exception = new ApiExceptionObject();
        exception.setMessage(ex.getMessage());
        exception.setHttpStatus(HttpStatus.NOT_FOUND);
        exception.setErrorCode(HttpStatus.NOT_FOUND.value());
        exception.setTimestamp(LocalDateTime.now());
        //exception.setThrowable(ex);
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomBadRequestException.class)
    public ResponseEntity<ApiExceptionObject> handleBadRequestException(CustomBadRequestException ex, WebRequest request) {
        ApiExceptionObject exception = new ApiExceptionObject();
        exception.setMessage(ex.getMessage());
        exception.setHttpStatus(HttpStatus.BAD_REQUEST);
        exception.setErrorCode(HttpStatus.BAD_REQUEST.value());
        exception.setTimestamp(LocalDateTime.now());
        //exception.setThrowable(ex);
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomUnauthorizedException.class)
    public ResponseEntity<ApiExceptionObject> handleUnauthorizedException(CustomUnauthorizedException ex, WebRequest request) {
        ApiExceptionObject exception = new ApiExceptionObject();
        exception.setMessage(ex.getMessage());
        exception.setHttpStatus(HttpStatus.UNAUTHORIZED);
        exception.setErrorCode(HttpStatus.UNAUTHORIZED.value());
        exception.setTimestamp(LocalDateTime.now());
        //exception.setThrowable(ex);
        return new ResponseEntity<>(exception, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
