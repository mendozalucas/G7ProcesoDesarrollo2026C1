package com.escrims.application.usecases;

import com.escrims.domain.model.usuario.Usuario;
import com.escrims.domain.repository.UsuarioRepository;
import com.escrims.infrastructure.security.PasswordHasher;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OAuthLoginUseCase {

    private final UsuarioRepository usuarioRepository;

    public OAuthLoginUseCase(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UUID execute(String proveedorNombre, String externalId, String email, String username) {
        String resolvedEmail = (email != null && !email.isBlank())
                ? email
                : proveedorNombre.toLowerCase() + "_" + externalId + "@oauth.local";

        return usuarioRepository.findByEmail(resolvedEmail)
                .map(Usuario::getId)
                .orElseGet(() -> crearUsuario(proveedorNombre, externalId, resolvedEmail, username));
    }

    private UUID crearUsuario(String proveedorNombre, String externalId, String email, String username) {
        String resolvedUsername = (username != null && !username.isBlank())
                ? username
                : proveedorNombre + "_" + externalId;

        if (usuarioRepository.findByUsername(resolvedUsername).isPresent()) {
            resolvedUsername = resolvedUsername + "_" + UUID.randomUUID().toString().substring(0, 8);
        }

        Usuario usuario = new Usuario(
                UUID.randomUUID(),
                resolvedUsername,
                email,
                PasswordHasher.hash(UUID.randomUUID().toString()));
        usuario.setVerificado(true);
        usuarioRepository.save(usuario);
        return usuario.getId();
    }
}
