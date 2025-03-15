package org.expensetracker.expensetrackerapi.model.expense;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseRequestDTO {

    @NotNull(message = "Category cannot be empty")
    private Category category;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private double amount;

    @NotBlank(message = "Description cannot be empty")
    private String description;
}
