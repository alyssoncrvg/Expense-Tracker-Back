package com.alysson.Expense.Tracker.api.controller;

import com.alysson.Expense.Tracker.api.dto.common.ApiResponse;
import com.alysson.Expense.Tracker.api.dto.dashboard.DashboardResponseDTO;
import com.alysson.Expense.Tracker.domain.model.User;
import com.alysson.Expense.Tracker.domain.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@Tag(name = "Dashboard", description = "Dados consolidados para a tela inicial")
public class DashboardController {

    @Autowired
    private DashboardService service;

    @GetMapping
    @Operation(summary = "Obter dados do dashboard", description = "Retorna saldo, gráfico de fluxo de caixa, gastos por categoria e últimas transações")
    public ResponseEntity<ApiResponse<DashboardResponseDTO>> getDashboard(@AuthenticationPrincipal User user) {
        var dashboardData = service.getDashboardData(user);
        return ResponseEntity.ok(ApiResponse.success(200, "Dados do dashboard recuperados com sucesso", dashboardData));
    }
}