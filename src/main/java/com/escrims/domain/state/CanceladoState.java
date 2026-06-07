package com.escrims.domain.state;

import com.escrims.domain.model.scrim.Scrim;

public class CanceladoState implements ScrimState {

    @Override
    public void avanzar(Scrim scrim) {
        throw new IllegalStateException("El scrim fue cancelado");
    }

    @Override
    public void cancelar(Scrim scrim) {
        throw new IllegalStateException("El scrim ya fue cancelado");
    }

    @Override
    public String getNombreEstado() { return "CANCELADO"; }
}
