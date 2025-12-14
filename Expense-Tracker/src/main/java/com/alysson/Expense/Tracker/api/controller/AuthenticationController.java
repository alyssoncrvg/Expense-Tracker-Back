package com.alysson.Expense.Tracker.api.controller;

import com.alysson.Expense.Tracker.api.dto.auth.AuthenticationDTO;
import com.alysson.Expense.Tracker.api.dto.auth.LoginResponseDTO;
import com.alysson.Expense.Tracker.api.dto.auth.RefreshTokenDTO;
import com.alysson.Expense.Tracker.api.dto.auth.RegisterDTO;
import com.alysson.Expense.Tracker.api.dto.common.ApiResponse;
import com.alysson.Expense.Tracker.domain.model.User;
import com.alysson.Expense.Tracker.domain.service.AuthorizationService;
import com.alysson.Expense.Tracker.domain.repository.UserRepository;
import com.alysson.Expense.Tracker.infra.security.TokenService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticacao", description = "Gerenciamento de logine cadastro de usuarios")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>>login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        User user = (User) auth.getPrincipal();
        var accessToken = tokenService.generateAccessToken(user);
        var refreshToken = tokenService.generateRefreshToken(user);

        return ResponseEntity.ok(ApiResponse.success(200, "Login realizado com sucesso", new LoginResponseDTO(accessToken, refreshToken)));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(@RequestBody @Valid RegisterDTO data) {
        authorizationService.register(data);
        return ResponseEntity.status(201).body(ApiResponse.success(201, "Usuário registrado com sucesso"));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> refreshToken(@RequestBody @Valid RefreshTokenDTO data) {
        String login = tokenService.validateToken(data.refreshToken());

        if (login.isEmpty()) {
            return ResponseEntity.status(403).build();
        }

        User user = (User) userRepository.findByEmail(login);

        if (user == null) {
            return ResponseEntity.status(403).build();
        }

        var newAccessToken = tokenService.generateAccessToken(user);
        var newRefreshToken = tokenService.generateRefreshToken(user);

        return ResponseEntity.ok(ApiResponse.success(200, "Login realizado com sucesso", new LoginResponseDTO(newAccessToken, newRefreshToken)));
    }
}