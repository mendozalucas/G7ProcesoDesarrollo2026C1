package com.escrims.infrastructure.adapters.auth;

import com.escrims.domain.model.usuario.Usuario;

public class OAuthManager {

    private IAdapterAuth autenticador;

    public OAuthManager(IAdapterAuth autenticador) {
        this.autenticador = autenticador;
    }

    public boolean autenticar(Usuario usuario) {
        return autenticador.autenticar(usuario);
    }
}
