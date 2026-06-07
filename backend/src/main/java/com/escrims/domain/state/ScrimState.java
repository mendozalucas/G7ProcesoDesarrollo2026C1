package com.escrims.domain.state;

import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.valueobjects.LadoEquipo;
import com.escrims.domain.valueobjects.RolJuego;

import java.util.UUID;

public interface ScrimState {

    void avanzar(Scrim scrim);

    void cancelar(Scrim scrim);

    String getNombreEstado();

    default void agregarJugador(Scrim scrim, UUID usuarioId, LadoEquipo lado, RolJuego rol) {
        throw new IllegalStateException("Solo se pueden agregar jugadores mientras se buscan participantes");
    }

    default void confirmarParticipacion(Scrim scrim, UUID usuarioId) {
        throw new IllegalStateException("Solo se puede confirmar con el lobby armado");
    }

    default void iniciar(Scrim scrim) {
        throw new IllegalStateException("Solo se puede iniciar un scrim confirmado");
    }

    default void finalizar(Scrim scrim) {
        throw new IllegalStateException("Solo se puede finalizar un scrim en juego");
    }

    default boolean permiteModificarRoles() {
        return false;
    }
}
