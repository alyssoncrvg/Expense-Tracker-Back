package com.alysson.Expense.Tracker.api.dto.dashboard;

import java.math.BigDecimal;
import java.util.List;

public record ChartDatasetDTO(
        String label,
        List<BigDecimal> data,
        String backgroundColor,
        String borderColor,
        Integer borderRadius,
        Integer borderWidth
) {}