package com.escrims.domain.state;

import com.escrims.domain.events.CanceladoEvent;
import com.escrims.domain.events.EnJuegoEvent;
import com.escrims.domain.model.scrim.Scrim;

public class ConfirmadoState implements ScrimState {

    @Override
    public void avanzar(Scrim scrim) {
        scrim.cambiarEstado(new EnJuegoState());
        scrim.agregarEvento(new EnJuegoEvent(scrim.getId()));
    }

    @Override
    public void cancelar(Scrim scrim) {
        scrim.cambiarEstado(new CanceladoState());
        scrim.agregarEvento(new CanceladoEvent(scrim.getId(), scrim.getMotivoCancelacion()));
    }

    @Override
    public void iniciar(Scrim scrim) {
        avanzar(scrim);
    }

    @Override
    public String getNombreEstado() { return "CONFIRMADO"; }
}
