package com.alysson.Expense.Tracker.domain.service;

import com.alysson.Expense.Tracker.api.dto.auth.RegisterDTO;
import com.alysson.Expense.Tracker.domain.model.User;
import com.alysson.Expense.Tracker.domain.repository.UserRepository;
import com.alysson.Expense.Tracker.infra.exception.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = repository.findByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }

        return user;
    }

    public void register(RegisterDTO data) {
        if (repository.findByEmail(data.email()) != null) {
            throw new UserAlreadyExistsException("Email já cadastrado no sistema.");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.name(), data.email(), encryptedPassword);

        repository.save(newUser);
    }
}