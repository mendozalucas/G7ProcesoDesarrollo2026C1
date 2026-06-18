package com.escrims.infrastructure.adapters.auth;

import com.escrims.domain.model.usuario.Usuario;

public interface IAdapterAuth {

    boolean autenticar(Usuario usuario);
}
