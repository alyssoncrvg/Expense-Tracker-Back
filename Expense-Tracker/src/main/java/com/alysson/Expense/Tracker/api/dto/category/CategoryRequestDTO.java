package com.alysson.Expense.Tracker.api.dto.category;
import jakarta.validation.constraints.NotBlank;

public record CategoryRequestDTO(
        @NotBlank(message = "O nome é obrigatório")
        String name
) {}