package com.escrims.domain.command;

import com.escrims.domain.model.lobby.GestorLobby;
import com.escrims.domain.model.usuario.Jugador;

public class SwapJugadoresCommand implements LobbyCommand {

    private final Jugador jugador1;
    private final Jugador jugador2;

    public SwapJugadoresCommand(Jugador jugador1, Jugador jugador2) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
    }

    @Override
    public void ejecutar(GestorLobby gestorLobby) {
        gestorLobby.swapJugadores(jugador1, jugador2);
    }

    @Override
    public void deshacer(GestorLobby gestorLobby) {
        gestorLobby.swapJugadores(jugador1, jugador2);
    }
}
