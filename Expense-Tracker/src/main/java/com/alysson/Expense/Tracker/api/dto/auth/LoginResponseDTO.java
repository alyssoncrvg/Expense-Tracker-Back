package com.alysson.Expense.Tracker.api.dto.auth;

public record LoginResponseDTO(String accessToken, String refreshToken) {
}