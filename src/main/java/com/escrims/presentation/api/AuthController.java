package com.escrims.presentation.api;

import com.escrims.application.usecases.RegisterUserUseCase;
import com.escrims.infrastructure.security.PasswordHasher;

import java.util.UUID;

/**
 * Controlador REST para autenticación.
 * En Spring Boot: @RestController @RequestMapping("/api/auth").
 */
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;

    public AuthController(RegisterUserUseCase registerUserUseCase) {
        this.registerUserUseCase = registerUserUseCase;
    }

    // POST /api/auth/register
    public UUID registrar(String username, String email, String password) {
        String hash = PasswordHasher.hash(password);
        return registerUserUseCase.execute(username, email, hash);
    }
}
