package com.escrims.domain.command;

import com.escrims.domain.model.lobby.GestorLobby;

public interface LobbyCommand {

    void ejecutar(GestorLobby gestorLobby);

    void deshacer(GestorLobby gestorLobby);
}
