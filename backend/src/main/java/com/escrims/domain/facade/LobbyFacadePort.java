package com.escrims.domain.facade;

import com.escrims.domain.command.LobbyCommand;
import com.escrims.domain.model.lobby.Lobby;

/** Contrato del facade de lobby (referenciado por {@link com.escrims.domain.model.usuario.Jugador}). */
public interface LobbyFacadePort {

    void ejecutarComando(LobbyCommand comando, Lobby lobby);

    void deshacerComando(Lobby lobby);

    void armarLobby(Lobby lobby);

    Lobby lobbyDesdeScrim(java.util.UUID scrimId);
}
