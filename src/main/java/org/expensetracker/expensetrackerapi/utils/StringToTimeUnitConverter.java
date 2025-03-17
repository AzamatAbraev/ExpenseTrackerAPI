package org.expensetracker.expensetrackerapi.utils;

import org.expensetracker.expensetrackerapi.model.expense.TimeUnit;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToTimeUnitConverter implements Converter<String, TimeUnit> {
    @Override
    public TimeUnit convert(String source) {
        return TimeUnit.fromString(source);
    }
}
