package com.alysson.Expense.Tracker.domain.repository;

import com.alysson.Expense.Tracker.api.dto.dashboard.ExpenseByCategoryDTO;
import com.alysson.Expense.Tracker.api.dto.dashboard.ExpenseByPersonDTO;
import com.alysson.Expense.Tracker.domain.enums.TransactionType;
import com.alysson.Expense.Tracker.domain.model.Transaction;
import com.alysson.Expense.Tracker.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findByUser(User user);

    // Soma valor total por tipo em um intervalo de datas para um usuário
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t " +
            "WHERE t.user = :user AND t.type = :type AND t.date BETWEEN :startDate AND :endDate")
    BigDecimal sumByTypeAndDateBetween(@Param("user") User user,
                                       @Param("type") TransactionType type,
                                       @Param("startDate") LocalDate startDate,
                                       @Param("endDate") LocalDate endDate);

    List<Transaction> findByUserOrderByDateDesc(User user, Pageable pageable);

    // Agrupa despesas por categoria no mês atual
    @Query("SELECT new com.alysson.Expense.Tracker.api.dto.dashboard.ExpenseByCategoryDTO(t.category.name, SUM(t.amount)) " +
            "FROM Transaction t " +
            "WHERE t.user = :user AND t.type = 'EXPENSE' AND t.date BETWEEN :startDate AND :endDate " +
            "GROUP BY t.category.name")
    List<ExpenseByCategoryDTO> sumExpensesByCategory(@Param("user") User user,
                                                     @Param("startDate") LocalDate startDate,
                                                     @Param("endDate") LocalDate endDate);

    // Agrupa despesas por PESSOA no mês atual
    @Query("SELECT new com.alysson.Expense.Tracker.api.dto.dashboard.ExpenseByPersonDTO(t.user.name, SUM(t.amount)) " +
            "FROM Transaction t " +
            "WHERE t.type = 'EXPENSE' AND t.date BETWEEN :startDate AND :endDate " +
            "GROUP BY t.user.name")
    List<ExpenseByPersonDTO> sumExpensesByPerson(@Param("startDate") LocalDate startDate,
                                                 @Param("endDate") LocalDate endDate);
}