package com.escrims.domain.state;

import com.escrims.domain.events.FinalizadoEvent;
import com.escrims.domain.model.scrim.Scrim;

public class EnJuegoState implements ScrimState {

    @Override
    public void avanzar(Scrim scrim) {
        scrim.cambiarEstado(new FinalizadoState());
        scrim.agregarEvento(new FinalizadoEvent(scrim.getId()));
    }

    @Override
    public void cancelar(Scrim scrim) {
        throw new IllegalStateException("No se puede cancelar una partida en curso");
    }

    @Override
    public void finalizar(Scrim scrim) {
        avanzar(scrim);
    }

    @Override
    public String getNombreEstado() { return "EN_JUEGO"; }
}
