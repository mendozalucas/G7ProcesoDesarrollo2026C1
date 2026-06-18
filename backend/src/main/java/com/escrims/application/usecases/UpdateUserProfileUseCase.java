package com.escrims.application.usecases;

import com.escrims.application.dto.UpdateProfileCommand;
import com.escrims.application.dto.UsuarioProfileDTO;
import com.escrims.domain.model.usuario.Usuario;
import com.escrims.domain.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateUserProfileUseCase {

    private final UsuarioRepository usuarioRepository;

    public UpdateUserProfileUseCase(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioProfileDTO execute(UUID usuarioId, UpdateProfileCommand command) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + usuarioId));

        if (command.getUsername() != null) {
            usuarioRepository.findByUsername(command.getUsername())
                    .filter(u -> !u.getId().equals(usuarioId))
                    .ifPresent(u -> {
                        throw new IllegalArgumentException("El username ya está en uso");
                    });
            usuario.setUsername(command.getUsername());
        }

        usuarioRepository.save(usuario);
        return UsuarioProfileDTO.from(usuario);
    }
}
