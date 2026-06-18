package com.escrims.application.usecases;

import com.escrims.domain.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VerifyEmailUseCase {

    private final UsuarioRepository usuarioRepository;

    public VerifyEmailUseCase(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void execute(UUID usuarioId) {
        usuarioRepository.findById(usuarioId).ifPresentOrElse(u -> {
            u.setVerificado(true);
            usuarioRepository.save(u);
        }, () -> {
            throw new IllegalArgumentException("Usuario no encontrado: " + usuarioId);
        });
    }
}
