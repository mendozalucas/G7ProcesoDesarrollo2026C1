package com.escrims.domain.command;

import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.model.usuario.Usuario;

public class AsignarRolCommand implements ScrimCommand {

    private final Usuario usuario;

    public AsignarRolCommand(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public void ejecutar(Scrim scrim) {
        throw new UnsupportedOperationException("AsignarRolCommand.ejecutar no implementado");
    }

    @Override
    public void deshacer(Scrim scrim) {
        throw new UnsupportedOperationException("AsignarRolCommand.deshacer no implementado");
    }
}
