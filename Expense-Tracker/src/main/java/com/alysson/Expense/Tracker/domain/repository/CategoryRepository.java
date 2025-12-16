package com.alysson.Expense.Tracker.domain.repository;
import com.alysson.Expense.Tracker.domain.model.Category;
import com.alysson.Expense.Tracker.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    List<Category> findByUser(User user);
    List<Category> findAll();
    boolean existsByNameAndUser(String name, User user);
}