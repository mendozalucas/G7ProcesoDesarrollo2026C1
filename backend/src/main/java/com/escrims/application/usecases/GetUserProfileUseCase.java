package com.escrims.application.usecases;

import com.escrims.application.dto.UsuarioProfileDTO;
import com.escrims.domain.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetUserProfileUseCase {

    private final UsuarioRepository usuarioRepository;

    public GetUserProfileUseCase(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioProfileDTO execute(UUID usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .map(UsuarioProfileDTO::from)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + usuarioId));
    }
}
