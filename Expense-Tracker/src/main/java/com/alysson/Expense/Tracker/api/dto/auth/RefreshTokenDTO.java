package com.alysson.Expense.Tracker.api.dto.auth;
import jakarta.validation.constraints.NotBlank;

public record RefreshTokenDTO(
        @NotBlank String refreshToken
) {}