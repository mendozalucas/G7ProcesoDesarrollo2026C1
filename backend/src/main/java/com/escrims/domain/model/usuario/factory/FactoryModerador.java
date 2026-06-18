package com.escrims.domain.model.usuario.factory;

import com.escrims.domain.model.usuario.Moderador;

import java.util.UUID;

public class FactoryModerador implements FactoryUsuario {

    @Override
    public Moderador crearUsuario(UUID id, String username, String email, String passwordHash) {
        return new Moderador(id, username, email, passwordHash);
    }
}
