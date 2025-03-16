package org.expensetracker.expensetrackerapi.service.implementation;

import lombok.RequiredArgsConstructor;
import org.expensetracker.expensetrackerapi.exception.CustomBadRequestException;
import org.expensetracker.expensetrackerapi.exception.CustomUnauthorizedException;
import org.expensetracker.expensetrackerapi.exception.CustomNotFoundException;
import org.expensetracker.expensetrackerapi.model.expense.Expense;
import org.expensetracker.expensetrackerapi.model.expense.ExpenseDTO;
import org.expensetracker.expensetrackerapi.model.expense.ExpenseRequestDTO;
import org.expensetracker.expensetrackerapi.model.user.User;
import org.expensetracker.expensetrackerapi.repository.ExpenseRepository;
import org.expensetracker.expensetrackerapi.repository.UserRepository;
import org.expensetracker.expensetrackerapi.service.ExpenseService;
import org.expensetracker.expensetrackerapi.utils.DateValidatorUtil;
import org.expensetracker.expensetrackerapi.utils.ExpenseMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    @Override
    public List<ExpenseDTO> getExpenses(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new CustomNotFoundException("User not found"));

        List<ExpenseDTO> expenses = new ArrayList<>();

        for (Expense expense : expenseRepository.findByUser(user)) {
            expenses.add(ExpenseMapper.toDTO(expense));
        }

        return expenses;
    }

    @Override
    public List<ExpenseDTO> getExpensesByDate(String email, String startDate, String endDate) {
        try {
            User user = userRepository.findByEmail(email).orElseThrow(() -> new CustomNotFoundException("User not found"));

            LocalDateTime startDateTime = LocalDate.parse(startDate, formatter).atStartOfDay();
            LocalDateTime endDateTime = LocalDate.parse(endDate, formatter).atTime(23, 59, 59);

            List<ExpenseDTO> expenses = new ArrayList<>();

            for (Expense expense : expenseRepository.findByUserAndDateRange(user, startDateTime, endDateTime)) {
                expenses.add(ExpenseMapper.toDTO(expense));
            }

            return expenses;
        } catch (DateTimeParseException e) {
            throw new CustomBadRequestException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred", e);
        }

    }

    @Override
    public ExpenseDTO getExpenseById(Long id, String email) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("Expense not found"));

        if (!expense.getUser().getEmail().equals(email)) {
            throw new CustomUnauthorizedException("Unauthorized access");
        }

        return ExpenseMapper.toDTO(expense);
    }

    @Override
    public ExpenseDTO createExpense(ExpenseRequestDTO expenseRequestDTO, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomNotFoundException("User not found"));

        Expense expense = ExpenseMapper.toEntity(expenseRequestDTO, user);

        return ExpenseMapper.toDTO(expenseRepository.save(expense));
    }

    @Override
    public ExpenseDTO updateExpense(Long id, ExpenseRequestDTO expenseRequestDTO, String email) {
        Expense expense = expenseRepository.findById(id).orElseThrow(() -> new CustomNotFoundException("Expense not found"));

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
        Expense expense = expenseRepository.findById(id).orElseThrow(() -> new CustomNotFoundException("Expense not found"));

        if (!expense.getUser().getEmail().equals(email)) {
            throw new CustomUnauthorizedException("Unauthorized access");
        }

        expenseRepository.delete(expense);
    }
}
