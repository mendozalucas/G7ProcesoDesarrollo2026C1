package com.escrims.domain.command;

import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.model.usuario.Usuario;

public class InvitarJugadoresCommand implements ScrimCommand {

    private Usuario usuario;

    public InvitarJugadoresCommand(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public void ejecutar(Scrim scrim) {
        throw new UnsupportedOperationException("InvitarJugadoresCommand.ejecutar no implementado");
    }

    @Override
    public void deshacer(Scrim scrim) {
        throw new UnsupportedOperationException("InvitarJugadoresCommand.deshacer no implementado");
    }
}
