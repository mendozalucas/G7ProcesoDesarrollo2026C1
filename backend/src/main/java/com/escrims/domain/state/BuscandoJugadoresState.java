package com.escrims.domain.state;

import com.escrims.domain.events.CanceladoEvent;
import com.escrims.domain.events.LobbyArmadoEvent;
import com.escrims.domain.model.scrim.Scrim;

import java.util.List;

public class BuscandoJugadoresState implements ScrimState {

    @Override
    public void avanzar(Scrim scrim) {
        scrim.cambiarEstado(new LobbyArmadoState());
        scrim.agregarEvento(new LobbyArmadoEvent(scrim.getId(), List.of()));
    }

    @Override
    public void cancelar(Scrim scrim) {
        scrim.cambiarEstado(new CanceladoState());
        scrim.agregarEvento(new CanceladoEvent(scrim.getId(), scrim.getMotivoCancelacion()));
    }

    @Override
    public String getNombreEstado() { return "BUSCANDO"; }
}
