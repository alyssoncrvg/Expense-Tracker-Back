package com.alysson.Expense.Tracker.api.dto.category;
import com.alysson.Expense.Tracker.domain.model.Category;

import java.util.UUID;

public record CategoryResponseDTO(UUID id, String name) {
    public CategoryResponseDTO(Category category) {
        this(category.getId(), category.getName());
    }
}