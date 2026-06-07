package com.escrims.domain.command;

import com.escrims.domain.model.usuario.Usuario;
import com.escrims.domain.valueobjects.LadoEquipo;
import com.escrims.domain.valueobjects.RolJuego;

/** Alias de compatibilidad con el nombre previo al refactor. */
public class InvitarJugadorCommand extends InvitarJugadoresCommand {

    public InvitarJugadorCommand(Usuario usuario, LadoEquipo lado, RolJuego rol) {
        super(usuario, lado, rol);
    }
}
