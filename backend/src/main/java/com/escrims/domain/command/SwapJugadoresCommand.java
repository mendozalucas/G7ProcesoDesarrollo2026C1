package com.escrims.domain.command;

import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.model.usuario.Usuario;

public class SwapJugadoresCommand implements ScrimCommand {

    private final Usuario jugador1;
    private final Usuario jugador2;

    public SwapJugadoresCommand(Usuario jugador1, Usuario jugador2) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
    }

    @Override
    public void ejecutar(Scrim scrim) {
        throw new UnsupportedOperationException("SwapJugadoresCommand.ejecutar no implementado");
    }

    @Override
    public void deshacer(Scrim scrim) {
        throw new UnsupportedOperationException("SwapJugadoresCommand.deshacer no implementado");
    }
}
