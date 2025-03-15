package org.expensetracker.expensetrackerapi.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApiExceptionObject {
    private String message;
    private HttpStatus httpStatus;
    private int errorCode;
    private LocalDateTime timestamp;
    private Throwable throwable;
}
