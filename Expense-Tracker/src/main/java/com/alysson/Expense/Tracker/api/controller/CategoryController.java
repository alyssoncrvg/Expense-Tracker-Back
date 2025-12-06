package com.alysson.Expense.Tracker.api.controller;

import com.alysson.Expense.Tracker.api.dto.category.CategoryRequestDTO;
import com.alysson.Expense.Tracker.api.dto.category.CategoryResponseDTO;
import com.alysson.Expense.Tracker.domain.model.User;
import com.alysson.Expense.Tracker.domain.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAll(@AuthenticationPrincipal User user) {
        List<CategoryResponseDTO> categories = service.getAll(user);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getById(@PathVariable UUID id, @AuthenticationPrincipal User user) {
        var category = service.getById(id, user);
        return ResponseEntity.ok(category);
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> create(@RequestBody @Valid CategoryRequestDTO data, @AuthenticationPrincipal User user) {
        var newCategory = service.create(data, user);
        return ResponseEntity.status(201).body(newCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> update(@PathVariable UUID id, @RequestBody @Valid CategoryRequestDTO data, @AuthenticationPrincipal User user) {
        var updatedCategory = service.update(id, data, user);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id, @AuthenticationPrincipal User user) {
        service.delete(id, user);
        return ResponseEntity.noContent().build();
    }
}