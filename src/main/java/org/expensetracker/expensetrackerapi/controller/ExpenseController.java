package org.expensetracker.expensetrackerapi.controller;

import jakarta.validation.Valid;
import org.expensetracker.expensetrackerapi.exception.CustomBadRequestException;
import org.expensetracker.expensetrackerapi.model.expense.ExpenseDTO;
import org.expensetracker.expensetrackerapi.model.expense.ExpenseRequestDTO;
import org.expensetracker.expensetrackerapi.model.expense.TimeUnit;
import org.expensetracker.expensetrackerapi.service.implementation.ExpenseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.expensetracker.expensetrackerapi.utils.DateValidatorUtil;

import java.util.List;

@RestController
@RequestMapping("api/v1/expenses")
@Validated
public class ExpenseController {
    private final ExpenseServiceImpl expenseService;

    @Autowired
    public ExpenseController(ExpenseServiceImpl expenseService) {
        this.expenseService = expenseService;
    }


    @GetMapping
    public ResponseEntity<List<ExpenseDTO>> getAllExpenses(@AuthenticationPrincipal UserDetails userDetails) {
        List<ExpenseDTO> expenses = expenseService.getExpenses(userDetails.getUsername());
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/filter/range")
    public ResponseEntity<List<ExpenseDTO>> getAllExpensesByDate(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam String startDate,
            @RequestParam String endDate
    ) {
        List<ExpenseDTO> expenses = expenseService.getExpensesByDate(userDetails.getUsername(), startDate, endDate);
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/filter/since")
    public ResponseEntity<List<ExpenseDTO>> getAllExpenseByDateSince(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam TimeUnit unit,
            @RequestParam Integer value) {
        if (unit == null || value == null) {
            throw new CustomBadRequestException("Both 'unit' and 'value' must be provided.");
        }

        if (value <= 0) {
            throw new CustomBadRequestException("Value cannot be negative");
        }

        List<ExpenseDTO> expenses = expenseService.getExpensesByDateSince(userDetails.getUsername(), unit, value);
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseDTO> getExpenseById(@PathVariable Long id,
                                                     @AuthenticationPrincipal UserDetails userDetails) {
        ExpenseDTO expense = expenseService.getExpenseById(id, userDetails.getUsername());
        return ResponseEntity.ok(expense);
    }

    @PostMapping
    public ResponseEntity<ExpenseDTO> createExpense(@Valid @RequestBody ExpenseRequestDTO expenseRequestDTO,
                                                    @AuthenticationPrincipal UserDetails userDetails) {
        ExpenseDTO expense = expenseService.createExpense(expenseRequestDTO, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(expense);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseDTO> updateExpense(@PathVariable Long id,
                                                    @RequestBody ExpenseRequestDTO expenseRequestDTO,
                                                    @AuthenticationPrincipal UserDetails userDetails) {

        ExpenseDTO expense = expenseService.updateExpense(id, expenseRequestDTO, userDetails.getUsername());
        return ResponseEntity.ok(expense);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id,
                                                @AuthenticationPrincipal UserDetails userDetails
                                                ) {
        expenseService.deleteExpense(id, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}
