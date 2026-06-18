package com.escrims.domain.command;

import com.escrims.domain.model.lobby.GestorLobby;
import com.escrims.domain.model.rol.Rol;
import com.escrims.domain.model.usuario.Jugador;

public class AsignarRolCommand implements LobbyCommand {

    private final Jugador jugador;
    private final Rol rol;
    private Rol rolAnterior;

    public AsignarRolCommand(Jugador jugador, Rol rol) {
        this.jugador = jugador;
        this.rol = rol;
    }

    public AsignarRolCommand(Jugador jugador, String rolNombre) {
        this(jugador, new Rol(null, rolNombre));
    }

    @Override
    public void ejecutar(GestorLobby gestorLobby) {
        rolAnterior = gestorLobby.getRolDe(jugador);
        gestorLobby.asignarRol(jugador, rol);
    }

    @Override
    public void deshacer(GestorLobby gestorLobby) {
        if (rolAnterior == null) {
            gestorLobby.quitarRol(jugador);
        } else {
            gestorLobby.asignarRol(jugador, rolAnterior);
        }
    }
}
