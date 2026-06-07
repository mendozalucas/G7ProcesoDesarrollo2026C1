package com.escrims.application.usecases;

import com.escrims.domain.repository.UsuarioRepository;
import com.escrims.infrastructure.security.PasswordHasher;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LoginUseCase {

    private final UsuarioRepository usuarioRepository;

    public LoginUseCase(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UUID execute(String email, String password) {
        return usuarioRepository.findByEmail(email)
                .filter(u -> PasswordHasher.verificar(password, u.getPasswordHash()))
                .map(u -> u.getId())
                .orElseThrow(() -> new IllegalArgumentException("Credenciales inválidas"));
    }
}
