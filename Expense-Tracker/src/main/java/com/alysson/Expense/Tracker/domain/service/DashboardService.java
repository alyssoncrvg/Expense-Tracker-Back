package com.alysson.Expense.Tracker.domain.service;

import com.alysson.Expense.Tracker.api.dto.dashboard.*;
import com.alysson.Expense.Tracker.api.dto.transaction.TransactionResponseDTO;
import com.alysson.Expense.Tracker.domain.enums.TransactionType;
import com.alysson.Expense.Tracker.domain.model.User;
import com.alysson.Expense.Tracker.domain.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class DashboardService {

    @Autowired
    private TransactionRepository transactionRepository;

    public DashboardResponseDTO getDashboardData(User user) {
        LocalDate now = LocalDate.now();
        YearMonth currentMonth = YearMonth.from(now);
        LocalDate startOfMonth = currentMonth.atDay(1);
        LocalDate endOfMonth = currentMonth.atEndOfMonth();

        BigDecimal monthlyIncome = transactionRepository.sumByTypeAndDateBetween(
                user, TransactionType.INCOME, startOfMonth, endOfMonth);

        BigDecimal monthlyExpense = transactionRepository.sumByTypeAndDateBetween(
                user, TransactionType.EXPENSE, startOfMonth, endOfMonth);

        BigDecimal balance = monthlyIncome.subtract(monthlyExpense);

        ChartDataDTO cashFlowChart = buildCashFlowChart(user, currentMonth);

        List<ExpenseByCategoryDTO> expensesByCategory = transactionRepository.sumExpensesByCategory(
                user, startOfMonth, endOfMonth);

        List<ExpenseByPersonDTO> expensesByPerson = transactionRepository.sumExpensesByPerson(
                startOfMonth, endOfMonth);

        List<TransactionResponseDTO> lastTransactions = transactionRepository.findByUserOrderByDateDesc(
                        user, PageRequest.of(0, 5))
                .stream()
                .map(TransactionResponseDTO::new)
                .toList();

        return new DashboardResponseDTO(
                balance,
                monthlyIncome,
                monthlyExpense,
                cashFlowChart,
                expensesByCategory,
                expensesByPerson,
                lastTransactions
        );
    }

    private ChartDataDTO buildCashFlowChart(User user, YearMonth currentMonth) {
        List<String> labels = new ArrayList<>();
        List<BigDecimal> incomeData = new ArrayList<>();
        List<BigDecimal> expenseData = new ArrayList<>();

        for (int i = 5; i >= 0; i--) {
            YearMonth targetMonth = currentMonth.minusMonths(i);
            LocalDate start = targetMonth.atDay(1);
            LocalDate end = targetMonth.atEndOfMonth();

            String monthName = targetMonth.getMonth()
                    .getDisplayName(TextStyle.SHORT, new Locale("pt", "BR"));
            labels.add(monthName.substring(0, 1).toUpperCase() + monthName.substring(1));

            BigDecimal income = transactionRepository.sumByTypeAndDateBetween(user, TransactionType.INCOME, start, end);
            BigDecimal expense = transactionRepository.sumByTypeAndDateBetween(user, TransactionType.EXPENSE, start, end);

            incomeData.add(income);
            expenseData.add(expense);
        }

        ChartDatasetDTO incomeDataset = new ChartDatasetDTO(
                "Receitas",
                incomeData,
                "rgba(34, 197, 94, 0.7)", // Verde fundo
                "rgb(34, 197, 94)",       // Verde borda
                6,
                1
        );

        ChartDatasetDTO expenseDataset = new ChartDatasetDTO(
                "Despesas",
                expenseData,
                "rgba(239, 68, 68, 0.7)", // Vermelho fundo
                "rgb(239, 68, 68)",       // Vermelho borda
                6,
                1
        );

        return new ChartDataDTO(labels, List.of(incomeDataset, expenseDataset));
    }
}