package com.escrims.domain.command;

import com.escrims.domain.model.lobby.GestorLobby;
import com.escrims.domain.model.usuario.Jugador;

public class InvitarJugadoresCommand implements LobbyCommand {

    private final Jugador jugador;
    private boolean invitado;

    public InvitarJugadoresCommand(Jugador jugador) {
        this.jugador = jugador;
    }

    @Override
    public void ejecutar(GestorLobby gestorLobby) {
        invitado = !gestorLobby.contieneJugador(jugador);
        if (invitado) {
            gestorLobby.invitarJugador(jugador);
        }
    }

    @Override
    public void deshacer(GestorLobby gestorLobby) {
        if (invitado) {
            gestorLobby.quitarJugador(jugador);
        }
    }
}
