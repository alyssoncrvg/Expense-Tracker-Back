package com.alysson.Expense.Tracker.api.dto.transaction;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record TransactionRequestDTO(
        @NotBlank(message = "A descrição é obrigatória")
        String description,

        @NotNull(message = "O valor é obrigatório")
        @Positive(message = "O valor deve ser positivo")
        BigDecimal amount,

        @NotBlank(message = "A data é obrigatória")
        String date,

        @NotBlank(message = "O tipo da transação é obrigatório")
        String type,

        @NotBlank(message = "A categoria é obrigatória")
        String categoryId
) {}