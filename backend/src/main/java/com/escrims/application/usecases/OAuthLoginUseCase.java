package com.escrims.application.usecases;

import com.escrims.domain.model.usuario.Jugador;
import com.escrims.domain.model.usuario.factory.FactoryJugador;
import com.escrims.domain.repository.UsuarioRepository;
import com.escrims.infrastructure.security.PasswordHasher;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OAuthLoginUseCase {

    private final UsuarioRepository usuarioRepository;
    private final FactoryJugador factoryJugador;

    public OAuthLoginUseCase(UsuarioRepository usuarioRepository, FactoryJugador factoryJugador) {
        this.usuarioRepository = usuarioRepository;
        this.factoryJugador = factoryJugador;
    }

    public UUID execute(String proveedorNombre, String externalId, String email, String username) {
        String resolvedEmail = (email != null && !email.isBlank())
                ? email
                : proveedorNombre.toLowerCase() + "_" + externalId + "@oauth.local";

        return usuarioRepository.findByEmail(resolvedEmail)
                .map(u -> u.getId())
                .orElseGet(() -> crearUsuario(proveedorNombre, externalId, resolvedEmail, username));
    }

    private UUID crearUsuario(String proveedorNombre, String externalId, String email, String username) {
        String resolvedUsername = (username != null && !username.isBlank())
                ? username
                : proveedorNombre + "_" + externalId;

        if (usuarioRepository.findByUsername(resolvedUsername).isPresent()) {
            resolvedUsername = resolvedUsername + "_" + UUID.randomUUID().toString().substring(0, 8);
        }

        Jugador jugador = factoryJugador.crearUsuario(
                UUID.randomUUID(),
                resolvedUsername,
                email,
                PasswordHasher.hash(UUID.randomUUID().toString()));
        jugador.setVerificado(true);
        usuarioRepository.save(jugador);
        return jugador.getId();
    }
}
