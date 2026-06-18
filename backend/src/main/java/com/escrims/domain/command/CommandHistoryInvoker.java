package com.escrims.domain.command;

import com.escrims.domain.model.lobby.GestorLobby;

/**
 * Invocador del historial de comandos sobre un lobby (patrón Command + undo).
 */
public class CommandHistoryInvoker {

    private final LobbyCommandHistory historial = new LobbyCommandHistory();

    public void ejecutar(LobbyCommand comando, GestorLobby gestorLobby) {
        historial.ejecutar(comando, gestorLobby);
    }

    public void deshacer(GestorLobby gestorLobby) {
        historial.deshacer(gestorLobby);
    }

    public boolean hayHistorial() {
        return historial.hayHistorial();
    }

    public void limpiar() {
        historial.limpiar();
    }
}
