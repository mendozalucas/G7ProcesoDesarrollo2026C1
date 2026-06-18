package com.escrims.domain.model.usuario.factory;

import com.escrims.domain.model.usuario.Jugador;

import java.util.UUID;

public class FactoryJugador implements FactoryUsuario {

    @Override
    public Jugador crearUsuario(UUID id, String username, String email, String passwordHash) {
        return new Jugador(id, username, email, passwordHash);
    }
}
