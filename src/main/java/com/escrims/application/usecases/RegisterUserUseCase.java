package com.escrims.application.usecases;

import com.escrims.domain.model.usuario.Usuario;
import com.escrims.domain.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RegisterUserUseCase {

    private final UsuarioRepository usuarioRepository;

    public RegisterUserUseCase(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UUID execute(String username, String email, String passwordHash) {
        usuarioRepository.findByEmail(email).ifPresent(u -> {
            throw new IllegalArgumentException("Ya existe un usuario con ese email");
        });
        Usuario usuario = new Usuario(UUID.randomUUID(), username, email, passwordHash);
        usuarioRepository.save(usuario);
        return usuario.getId();
    }
}
