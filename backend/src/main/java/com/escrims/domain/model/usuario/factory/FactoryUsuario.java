package com.escrims.domain.model.usuario.factory;

import com.escrims.domain.model.usuario.Usuario;

import java.util.UUID;

public interface FactoryUsuario {

    Usuario crearUsuario(UUID id, String username, String email, String passwordHash);
}
