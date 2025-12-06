package com.alysson.Expense.Tracker.domain.service;

import com.alysson.Expense.Tracker.api.dto.category.CategoryRequestDTO;
import com.alysson.Expense.Tracker.api.dto.category.CategoryResponseDTO;
import com.alysson.Expense.Tracker.domain.model.Category;
import com.alysson.Expense.Tracker.domain.model.User;
import com.alysson.Expense.Tracker.domain.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public List<CategoryResponseDTO> getAll(User user) {
        return repository.findByUser(user)
                .stream()
                .map(CategoryResponseDTO::new)
                .collect(Collectors.toList());
    }

    public CategoryResponseDTO create(CategoryRequestDTO data, User user) {
        Category category = new Category();
        category.setName(data.name());
        category.setUser(user);

        repository.save(category);
        return new CategoryResponseDTO(category);
    }

    public CategoryResponseDTO update(UUID id, CategoryRequestDTO data, User user) {
        Category category = getCategoryValidated(id, user);
        category.setName(data.name());

        repository.save(category);
        return new CategoryResponseDTO(category);
    }

    public void delete(UUID id, User user) {
        Category category = getCategoryValidated(id, user);
        repository.delete(category);
    }

    public CategoryResponseDTO getById(UUID id, User user) {
        Category category = getCategoryValidated(id, user);
        return new CategoryResponseDTO(category);
    }

    private Category getCategoryValidated(UUID id, User user) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

        if (!category.getUser().getId().equals(user.getId())) {
            throw new EntityNotFoundException("Categoria não encontrada ou acesso negado");
        }

        return category;
    }
}