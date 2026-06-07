package com.escrims.domain.state;

import com.escrims.domain.events.CanceladoEvent;
import com.escrims.domain.events.ConfirmadoEvent;
import com.escrims.domain.model.scrim.Scrim;

public class LobbyArmadoState implements ScrimState {

    @Override
    public void avanzar(Scrim scrim) {
        if (!scrim.todosConfirmaron()) {
            throw new IllegalStateException("No se puede avanzar: faltan confirmaciones");
        }
        scrim.cambiarEstado(new ConfirmadoState());
        scrim.agregarEvento(new ConfirmadoEvent(scrim.getId(), scrim.getParticipantesIds()));
    }

    @Override
    public void cancelar(Scrim scrim) {
        scrim.cambiarEstado(new CanceladoState());
        scrim.agregarEvento(new CanceladoEvent(scrim.getId(), scrim.getMotivoCancelacion()));
    }

    @Override
    public String getNombreEstado() { return "LOBBY_ARMADO"; }
}
