package com.alysson.Expense.Tracker.domain.service;

import com.alysson.Expense.Tracker.api.dto.transaction.TransactionRequestDTO;
import com.alysson.Expense.Tracker.api.dto.transaction.TransactionResponseDTO;
import com.alysson.Expense.Tracker.domain.enums.TransactionType;
import com.alysson.Expense.Tracker.domain.model.Category;
import com.alysson.Expense.Tracker.domain.model.Transaction;
import com.alysson.Expense.Tracker.domain.model.User;
import com.alysson.Expense.Tracker.domain.repository.CategoryRepository;
import com.alysson.Expense.Tracker.domain.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    public TransactionResponseDTO create(TransactionRequestDTO data, User user) {
        UUID categoryId = parseUUID(data.categoryId(), "ID da categoria");
        Category category = validateCategory(categoryId, user);
        LocalDate transactionDate = parseDate(data.date());
        TransactionType transactionType = parseType(data.type());

        Transaction transaction = new Transaction();
        transaction.setDescription(data.description());
        transaction.setAmount(data.amount());
        transaction.setDate(transactionDate);
        transaction.setType(transactionType);
        transaction.setCategory(category);
        transaction.setUser(user);

        repository.save(transaction);
        return new TransactionResponseDTO(transaction);
    }

    public List<TransactionResponseDTO> getAll(User user) {
        return repository.findByUser(user)
                .stream()
                .map(TransactionResponseDTO::new)
                .collect(Collectors.toList());
    }

    public TransactionResponseDTO getById(UUID id, User user) {
        Transaction transaction = getTransactionValidated(id, user);
        return new TransactionResponseDTO(transaction);
    }

    public TransactionResponseDTO update(UUID id, TransactionRequestDTO data, User user) {
        Transaction transaction = getTransactionValidated(id, user);

        UUID categoryId = parseUUID(data.categoryId(), "UUID da categoria");
        Category category = validateCategory(categoryId, user);
        LocalDate transactionDate = parseDate(data.date());
        TransactionType transactionType = parseType(data.type());

        transaction.setDescription(data.description());
        transaction.setAmount(data.amount());
        transaction.setDate(transactionDate);
        transaction.setType(transactionType);
        transaction.setCategory(category);

        repository.save(transaction);
        return new TransactionResponseDTO(transaction);
    }

    public void delete(UUID id, User user) {
        Transaction transaction = getTransactionValidated(id, user);
        repository.delete(transaction);
    }

    private UUID parseUUID(String uuidString, String fieldName) {
        try {
            return UUID.fromString(uuidString);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Formato inválido para " + fieldName + ". Esperado um UUID válido.");
        }
    }

    private LocalDate parseDate(String dateString) {
        try {
            return LocalDate.parse(dateString);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Data inválida ou formato incorreto. Use o formato AAAA-MM-DD (ex: 2025-12-30).");
        }
    }

    private TransactionType parseType(String typeString) {
        try {
            return TransactionType.valueOf(typeString.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de transação inválido. Valores aceitos: INCOME, EXPENSE.");
        }
    }

    private Transaction getTransactionValidated(UUID id, User user) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transação não encontrada"));
    }

    private Category validateCategory(UUID categoryId, User user) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada com o UUID " + categoryId +  " informado"));
    }
}