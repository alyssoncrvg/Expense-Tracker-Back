package com.alysson.Expense.Tracker.api.dto.dashboard;

import java.util.List;

public record ChartDataDTO(
        List<String> labels,
        List<ChartDatasetDTO> datasets
) {}