package org.expensetracker.expensetrackerapi.model.expense;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDTO {
    private Long id;
    private String category;
    private double amount;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
