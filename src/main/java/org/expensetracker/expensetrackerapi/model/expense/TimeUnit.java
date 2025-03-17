package org.expensetracker.expensetrackerapi.model.expense;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.expensetracker.expensetrackerapi.exception.CustomBadRequestException;

public enum TimeUnit {
    WEEK, MONTH;

    @JsonCreator
    public static TimeUnit fromString(String value) {
        for (TimeUnit unit : TimeUnit.values()) {
            if (unit.name().equalsIgnoreCase(value)) {
                return unit;
            }
        }
        throw new CustomBadRequestException("Invalid time unit: " + value);
    }

    @JsonValue
    public String toLowerCase() {
        return name().toLowerCase();
    }
}
