package com.alysson.Expense.Tracker.infra.exception;

import org.springframework.http.HttpStatus;

public record RestErrorMessage(int status, String message) {
}