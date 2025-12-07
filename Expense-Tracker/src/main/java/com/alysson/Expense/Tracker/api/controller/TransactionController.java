package com.alysson.Expense.Tracker.api.controller;

import com.alysson.Expense.Tracker.api.dto.common.ApiResponse;
import com.alysson.Expense.Tracker.api.dto.transaction.TransactionRequestDTO;
import com.alysson.Expense.Tracker.api.dto.transaction.TransactionResponseDTO;
import com.alysson.Expense.Tracker.domain.model.User;
import com.alysson.Expense.Tracker.domain.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
    @RequestMapping("/api/transactions")
@Tag(name = "Transações", description = "Gerenciamento de receitas e despesas")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @PostMapping
    @Operation(summary = "Criar nova transação", description = "Registra uma entrada ou saída financeira")
    public ResponseEntity<ApiResponse<TransactionResponseDTO>> create(@RequestBody @Valid TransactionRequestDTO data, @AuthenticationPrincipal User user) {
        var transaction = service.create(data, user);
        return ResponseEntity.status(201).body(ApiResponse.success(201, "Transação criada com sucesso", transaction));
    }

    @GetMapping
    @Operation(summary = "Listar transações", description = "Lista todas as transações do usuário logado")
    public ResponseEntity<ApiResponse<List<TransactionResponseDTO>>> getAll(@AuthenticationPrincipal User user) {
        var transactions = service.getAll(user);
        return ResponseEntity.ok(ApiResponse.success(200, "Lista de transações recuperada", transactions));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar transação", description = "Busca detalhes de uma transação específica por ID")
    public ResponseEntity<ApiResponse<TransactionResponseDTO>> getById(@PathVariable UUID id, @AuthenticationPrincipal User user) {
        var transaction = service.getById(id, user);
        return ResponseEntity.ok(ApiResponse.success(200, "Transação encontrada", transaction));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar transação", description = "Atualiza os dados de uma transação existente")
    public ResponseEntity<ApiResponse<TransactionResponseDTO>> update(@PathVariable UUID id, @RequestBody @Valid TransactionRequestDTO data, @AuthenticationPrincipal User user) {
        var transaction = service.update(id, data, user);
        return ResponseEntity.ok(ApiResponse.success(200, "Transação atualizada com sucesso", transaction));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar transação", description = "Remove uma transação do sistema")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id, @AuthenticationPrincipal User user) {
        service.delete(id, user);
        return ResponseEntity.ok(ApiResponse.success(200, "Transação removida com sucesso"));
    }
}