package org.expensetracker.expensetrackerapi.service.implementation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.expensetracker.expensetrackerapi.exception.CustomUnauthorizedException;
import org.expensetracker.expensetrackerapi.exception.ExpenseNotFoundException;
import org.expensetracker.expensetrackerapi.model.expense.Expense;
import org.expensetracker.expensetrackerapi.model.expense.ExpenseDTO;
import org.expensetracker.expensetrackerapi.model.expense.ExpenseRequestDTO;
import org.expensetracker.expensetrackerapi.model.user.User;
import org.expensetracker.expensetrackerapi.repository.ExpenseRepository;
import org.expensetracker.expensetrackerapi.repository.UserRepository;
import org.expensetracker.expensetrackerapi.service.ExpenseService;
import org.expensetracker.expensetrackerapi.utils.ExpenseMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    @Override
    public List<ExpenseDTO> getExpenses(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        List<ExpenseDTO> expenses = new ArrayList<>();

        for (Expense expense : expenseRepository.findByUser(user)) {
            expenses.add(ExpenseMapper.toDTO(expense));
        }

        return expenses;
    }

    @Override
    public ExpenseDTO getExpenseById(Long id, String email) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException("Expense not found"));

        if (!expense.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Unauthorized access");
        }

        return ExpenseMapper.toDTO(expense);
    }

    @Override
    public ExpenseDTO createExpense(ExpenseRequestDTO expenseRequestDTO, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Expense expense = ExpenseMapper.toEntity(expenseRequestDTO, user);

        return ExpenseMapper.toDTO(expenseRepository.save(expense));
    }

    @Override
    public ExpenseDTO updateExpense(Long id, ExpenseRequestDTO expenseRequestDTO, String email) {
        Expense expense = expenseRepository.findById(id).orElseThrow(() -> new ExpenseNotFoundException("Expense not found"));

        if (!expense.getUser().getEmail().equals(email)) {
            throw new CustomUnauthorizedException("Unauthorized access");
        }

        expense.setAmount(expenseRequestDTO.getAmount());
        expense.setDescription(expenseRequestDTO.getDescription());
        expense.setCategory(expenseRequestDTO.getCategory());

        return ExpenseMapper.toDTO(expenseRepository.save(expense));
    }

    @Override
    public void deleteExpense(Long id, String email) {
        Expense expense = expenseRepository.findById(id).orElseThrow(() -> new ExpenseNotFoundException("Expense not found"));

        if (!expense.getUser().getEmail().equals(email)) {
            throw new CustomUnauthorizedException("Unauthorized access");
        }

        expenseRepository.delete(expense);
    }
}
