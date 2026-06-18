package com.escrims.infrastructure.adapters.auth;

import com.escrims.domain.model.usuario.Usuario;

public class AdapterSteam implements IAdapterAuth {

    private SteamSDK adaptee;

    public AdapterSteam(SteamSDK adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public boolean autenticar(Usuario usuario) {
        return true;
    }
}
