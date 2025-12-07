package com.alysson.Expense.Tracker.api.dto.transaction;

import com.alysson.Expense.Tracker.api.dto.category.CategoryResponseDTO;
import com.alysson.Expense.Tracker.domain.enums.TransactionType;
import com.alysson.Expense.Tracker.domain.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record TransactionResponseDTO(
        UUID uuid,
        String description,
        BigDecimal amount,
        LocalDate date,
        TransactionType type,
        CategoryResponseDTO category
) {
    public TransactionResponseDTO(Transaction transaction) {
        this(
                transaction.getId(),
                transaction.getDescription(),
                transaction.getAmount(),
                transaction.getDate(),
                transaction.getType(),
                new CategoryResponseDTO(transaction.getCategory())
        );
    }
}