package com.escrims.domain.command;

import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.model.usuario.Usuario;
import com.escrims.domain.valueobjects.LadoEquipo;

public class SwapJugadoresCommand implements ScrimCommand {

    private final Usuario jugador1;
    private final Usuario jugador2;
    private final LadoEquipo lado;

    public SwapJugadoresCommand(Usuario jugador1, Usuario jugador2, LadoEquipo lado) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.lado = lado;
    }

    @Override
    public void ejecutar(Scrim scrim) {
        if (!scrim.permiteModificarRoles()) {
            throw new IllegalStateException("No se pueden intercambiar jugadores en el estado " + scrim.getEstadoNombre());
        }
        swap(scrim);
    }

    @Override
    public void deshacer(Scrim scrim) {
        swap(scrim);
    }

    private void swap(Scrim scrim) {
        scrim.getEquipos().stream()
                .filter(e -> e.getLado().equals(lado))
                .findFirst()
                .ifPresent(e -> e.swapParticipantes(jugador1.getId(), jugador2.getId()));
    }
}
