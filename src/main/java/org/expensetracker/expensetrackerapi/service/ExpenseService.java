package org.expensetracker.expensetrackerapi.service;


import org.expensetracker.expensetrackerapi.model.expense.ExpenseDTO;
import org.expensetracker.expensetrackerapi.model.expense.ExpenseRequestDTO;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ExpenseService {
    List<ExpenseDTO> getExpenses(String email);
    List<ExpenseDTO> getExpensesByDate(String email, String startDate, String endDate);
    ExpenseDTO getExpenseById(Long id, String email);
    ExpenseDTO createExpense(ExpenseRequestDTO expenseRequestDTO, String username);
    ExpenseDTO updateExpense(Long id, ExpenseRequestDTO expenseRequestDTO, String username);
    void deleteExpense(Long id, String username);
}
