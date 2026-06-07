package com.escrims.domain.command;

import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.model.usuario.Usuario;
import com.escrims.domain.valueobjects.LadoEquipo;
import com.escrims.domain.valueobjects.RolJuego;

public class InvitarJugadoresCommand implements ScrimCommand {

    private final Usuario usuario;
    private final LadoEquipo lado;
    private final RolJuego rol;
    private boolean invitacionActiva;

    public InvitarJugadoresCommand(Usuario usuario, LadoEquipo lado, RolJuego rol) {
        this.usuario = usuario;
        this.lado = lado;
        this.rol = rol;
    }

    @Override
    public void ejecutar(Scrim scrim) {
        scrim.agregarJugador(usuario.getId(), lado, rol);
        invitacionActiva = true;
    }

    @Override
    public void deshacer(Scrim scrim) {
        if (invitacionActiva) {
            scrim.getEquipos().stream()
                    .filter(e -> e.getLado().equals(lado))
                    .findFirst()
                    .ifPresent(e -> e.removerParticipante(usuario.getId()));
            invitacionActiva = false;
        }
    }
}
