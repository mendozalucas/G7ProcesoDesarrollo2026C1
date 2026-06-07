package com.escrims.domain.state;

import com.escrims.domain.events.CanceladoEvent;
import com.escrims.domain.events.LobbyArmadoEvent;
import com.escrims.domain.model.scrim.Scrim;

public class BuscandoJugadoresState implements ScrimState {

    @Override
    public void avanzar(Scrim scrim) {
        if (!scrim.estaCompleto()) {
            throw new IllegalStateException("No se puede avanzar: faltan jugadores");
        }
        if (!scrim.getJuego().validar(scrim)) {
            throw new IllegalStateException("Composición de equipos inválida para " + scrim.getJuego().getNombre());
        }
        scrim.cambiarEstado(new LobbyArmadoState());
        scrim.agregarEvento(new LobbyArmadoEvent(scrim.getId(), scrim.getParticipantesIds()));
    }

    @Override
    public void cancelar(Scrim scrim) {
        scrim.cambiarEstado(new CanceladoState());
        scrim.agregarEvento(new CanceladoEvent(scrim.getId(), scrim.getMotivoCancelacion()));
    }

    @Override
    public String getNombreEstado() { return "BUSCANDO"; }
}
