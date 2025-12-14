package com.alysson.Expense.Tracker.api.dto.dashboard;

import com.alysson.Expense.Tracker.api.dto.transaction.TransactionResponseDTO;

import java.math.BigDecimal;
import java.util.List;

public record DashboardResponseDTO(
        BigDecimal balance,
        BigDecimal monthlyIncome,
        BigDecimal monthlyExpense,
        ChartDataDTO cashFlowChart,
        List<ExpenseByCategoryDTO> expensesByCategory,
        List<ExpenseByPersonDTO> expensesByPerson,
        List<TransactionResponseDTO> lastTransactions
) {}