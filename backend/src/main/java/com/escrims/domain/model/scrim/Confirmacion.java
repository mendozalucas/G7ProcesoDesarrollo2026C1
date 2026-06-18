package com.escrims.domain.model.scrim;

import com.escrims.domain.model.usuario.Jugador;
import com.escrims.domain.model.usuario.Usuario;

public class Confirmacion {

    private Long id;
    private Jugador jugador;
    private Scrim scrim;
    private boolean confirmado;

    public Confirmacion(Long id, Jugador jugador, Scrim scrim, boolean confirmado) {
        this.id = id;
        this.jugador = jugador;
        this.scrim = scrim;
        this.confirmado = confirmado;
    }

    public void confirmar() {
        this.confirmado = true;
    }

    public void declinar() {
        this.confirmado = false;
    }

    public boolean esPendiente() {
        return !confirmado;
    }

    public Long getId() { return id; }
    public Jugador getJugador() { return jugador; }
    public Usuario getUsuario() { return jugador; }
    public Scrim getScrim() { return scrim; }
    public boolean isConfirmado() { return confirmado; }
}
