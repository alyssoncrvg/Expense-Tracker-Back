package com.alysson.Expense.Tracker.domain.repository;

import com.alysson.Expense.Tracker.domain.model.Transaction;
import com.alysson.Expense.Tracker.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findByUser(User user);
}