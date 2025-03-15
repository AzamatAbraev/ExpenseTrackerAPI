package org.expensetracker.expensetrackerapi.utils;

import lombok.experimental.UtilityClass;
import org.expensetracker.expensetrackerapi.model.expense.Category;
import org.expensetracker.expensetrackerapi.model.expense.Expense;
import org.expensetracker.expensetrackerapi.model.expense.ExpenseDTO;
import org.expensetracker.expensetrackerapi.model.expense.ExpenseRequestDTO;
import org.expensetracker.expensetrackerapi.model.user.User;

@UtilityClass
public class ExpenseMapper {

    public static ExpenseDTO toDTO(Expense expense) {
        if (expense == null) {
            return null;
        }
        return new ExpenseDTO(
                expense.getId(),
                expense.getCategory().toString(),
                expense.getAmount(),
                expense.getDescription(),
                expense.getCreatedAt(),
                expense.getUpdatedAt()
        );
    }

    public static Expense toEntity(ExpenseDTO dto, User user) {
        if (dto == null || user == null) {
            return null;
        }
        Expense expense = new Expense();
        expense.setCategory(Category.valueOf(dto.getCategory()));
        expense.setAmount(dto.getAmount());
        expense.setDescription(dto.getDescription());
        expense.setUser(user);
        return expense;
    }

    public static Expense toEntity(ExpenseRequestDTO dto, User user) {
        if (dto == null || user == null) {
            return null;
        }
        Expense expense = new Expense();
        expense.setCategory(dto.getCategory());
        expense.setAmount(dto.getAmount());
        expense.setDescription(dto.getDescription());
        expense.setUser(user);
        return expense;
    }
}
