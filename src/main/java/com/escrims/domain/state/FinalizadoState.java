package com.escrims.domain.state;

import com.escrims.domain.model.scrim.Scrim;

public class FinalizadoState implements ScrimState {

    @Override
    public void avanzar(Scrim scrim) {
        throw new IllegalStateException("El scrim ya finalizó");
    }

    @Override
    public void cancelar(Scrim scrim) {
        throw new IllegalStateException("El scrim ya finalizó");
    }

    @Override
    public String getNombreEstado() { return "FINALIZADO"; }
}
