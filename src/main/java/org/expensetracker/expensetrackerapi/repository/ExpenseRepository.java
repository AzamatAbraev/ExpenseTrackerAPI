package org.expensetracker.expensetrackerapi.repository;

import org.expensetracker.expensetrackerapi.model.expense.Expense;
import org.expensetracker.expensetrackerapi.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUser(User user);

    @Query("select e from Expense e where e.user = :user and e.createdAt between :startDate and :endDate")
    List<Expense> findByUserAndDateRange(
            @Param("user") User user,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}
