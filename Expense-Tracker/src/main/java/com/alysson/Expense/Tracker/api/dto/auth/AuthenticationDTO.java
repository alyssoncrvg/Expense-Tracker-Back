package com.alysson.Expense.Tracker.api.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthenticationDTO(
        @NotBlank(message = "Email nao pode estar em branco")
        @Email(message = "Formato de email incorreto")
        String email,
        @NotBlank(message = "Senha e obrigatorio")
        String password
) {
}