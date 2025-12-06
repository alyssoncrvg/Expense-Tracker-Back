package com.alysson.Expense.Tracker.api.dto.auth;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterDTO(
        @NotBlank(message = "O campo 'name' é obrigatório")
        String name,

        @NotBlank(message = "O campo 'email' é obrigatório")
        @Email(message = "Formato de e-mail inválido")
        String email,

        @NotBlank(message = "O campo 'password' é obrigatório")
        @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
        String password
) {}