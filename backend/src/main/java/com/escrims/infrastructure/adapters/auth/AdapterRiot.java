package com.escrims.infrastructure.adapters.auth;

import com.escrims.domain.model.usuario.Usuario;

public class AdapterRiot implements IAdapterAuth {

    private RiotSDK adaptee;

    public AdapterRiot(RiotSDK adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public boolean autenticar(Usuario usuario) {
        return true;
    }
}
