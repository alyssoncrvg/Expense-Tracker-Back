package com.alysson.Expense.Tracker.api.dto.dashboard;

import java.math.BigDecimal;

public record ExpenseByCategoryDTO(
        String categoryName,
        BigDecimal amount
) {}