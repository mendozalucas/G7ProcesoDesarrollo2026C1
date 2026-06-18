package com.escrims.domain.model.scrim;

import com.escrims.domain.model.usuario.Usuario;

public class Confirmacion {

    private Long id;
    private Usuario usuario;
    private Scrim scrim;
    private boolean confirmado;

    public Confirmacion(Long id, Usuario usuario, Scrim scrim, boolean confirmado) {
        this.id = id;
        this.usuario = usuario;
        this.scrim = scrim;
        this.confirmado = confirmado;
    }

    public void confirmar() {
        this.confirmado = true;
    }

    public boolean esPendiente() {
        return !confirmado;
    }

    public Long getId() { return id; }
    public Usuario getUsuario() { return usuario; }
    public Scrim getScrim() { return scrim; }
    public boolean isConfirmado() { return confirmado; }
}
