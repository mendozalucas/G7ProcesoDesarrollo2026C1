package com.escrims.domain.command;

import com.escrims.domain.model.lobby.GestorLobby;

import java.util.ArrayDeque;
import java.util.Deque;

public class LobbyCommandHistory {

    private final Deque<LobbyCommand> historial = new ArrayDeque<>();

    public void ejecutar(LobbyCommand comando, GestorLobby gestorLobby) {
        comando.ejecutar(gestorLobby);
        historial.push(comando);
    }

    public void deshacer(GestorLobby gestorLobby) {
        if (historial.isEmpty()) {
            throw new IllegalStateException("No hay comandos para deshacer");
        }
        historial.pop().deshacer(gestorLobby);
    }

    public boolean hayHistorial() {
        return !historial.isEmpty();
    }

    public void limpiar() {
        historial.clear();
    }
}
