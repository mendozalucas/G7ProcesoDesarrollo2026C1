package com.escrims.application.usecases;

import com.escrims.domain.model.usuario.Jugador;
import com.escrims.domain.model.usuario.factory.FactoryJugador;
import com.escrims.domain.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RegisterUserUseCase {

    private final UsuarioRepository usuarioRepository;
    private final FactoryJugador factoryJugador;

    public RegisterUserUseCase(UsuarioRepository usuarioRepository, FactoryJugador factoryJugador) {
        this.usuarioRepository = usuarioRepository;
        this.factoryJugador = factoryJugador;
    }

    public UUID execute(String username, String email, String passwordHash) {
        usuarioRepository.findByEmail(email).ifPresent(u -> {
            throw new IllegalArgumentException("Ya existe un usuario con ese email");
        });
        Jugador jugador = factoryJugador.crearUsuario(UUID.randomUUID(), username, email, passwordHash);
        usuarioRepository.save(jugador);
        return jugador.getId();
    }
}
