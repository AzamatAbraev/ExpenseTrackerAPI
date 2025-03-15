package org.expensetracker.expensetrackerapi.repository;

import org.expensetracker.expensetrackerapi.model.expense.Expense;
import org.expensetracker.expensetrackerapi.model.expense.ExpenseDTO;
import org.expensetracker.expensetrackerapi.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUser(User user);
}
