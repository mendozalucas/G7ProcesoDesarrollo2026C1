package com.escrims.infrastructure.adapters.auth;

import com.escrims.domain.model.usuario.Usuario;

public class AdapterDiscord implements IAdapterAuth {

    private DiscordSDK adaptee;

    public AdapterDiscord(DiscordSDK adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public boolean autenticar(Usuario usuario) {
        return true;
    }
}
