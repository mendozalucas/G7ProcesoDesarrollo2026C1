package com.escrims.domain.model.usuario.factory;

import com.escrims.domain.model.usuario.Usuario;

import java.util.UUID;

/**
 * Resuelve la factory concreta según el rol persistido (JUGADOR / MODERADOR).
 */
public final class UsuarioFactoryRegistry {

    private final FactoryJugador factoryJugador;
    private final FactoryModerador factoryModerador;

    public UsuarioFactoryRegistry(FactoryJugador factoryJugador, FactoryModerador factoryModerador) {
        this.factoryJugador = factoryJugador;
        this.factoryModerador = factoryModerador;
    }

    public Usuario crear(UUID id, String username, String email, String passwordHash, String rol) {
        return factoryPara(rol).crearUsuario(id, username, email, passwordHash);
    }

    private FactoryUsuario factoryPara(String rol) {
        if (rol != null && (rol.equalsIgnoreCase("MODERADOR") || rol.equalsIgnoreCase("MOD"))) {
            return factoryModerador;
        }
        return factoryJugador;
    }
}
