package com.escrims.domain.command;

import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.model.usuario.Usuario;
import com.escrims.domain.valueobjects.LadoEquipo;
import com.escrims.domain.valueobjects.RolJuego;

public class AsignarRolCommand implements ScrimCommand {

    private final Usuario usuario;
    private final LadoEquipo lado;
    private final RolJuego rolNuevo;
    private RolJuego rolAnterior;

    public AsignarRolCommand(Usuario usuario, LadoEquipo lado, RolJuego rolNuevo) {
        this.usuario = usuario;
        this.lado = lado;
        this.rolNuevo = rolNuevo;
    }

    @Override
    public void ejecutar(Scrim scrim) {
        if (!scrim.permiteModificarRoles()) {
            throw new IllegalStateException("No se pueden modificar roles en el estado " + scrim.getEstadoNombre());
        }
        rolAnterior = scrim.getEquipos().stream()
                .filter(e -> e.getLado().equals(lado))
                .findFirst()
                .flatMap(e -> e.getParticipante(usuario.getId()))
                .map(p -> p.getRolAsignado())
                .orElse(null);

        scrim.getEquipos().stream()
                .filter(e -> e.getLado().equals(lado))
                .findFirst()
                .ifPresent(e -> e.asignarRol(usuario.getId(), rolNuevo));
    }

    @Override
    public void deshacer(Scrim scrim) {
        if (rolAnterior != null) {
            scrim.getEquipos().stream()
                    .filter(e -> e.getLado().equals(lado))
                    .findFirst()
                    .ifPresent(e -> e.asignarRol(usuario.getId(), rolAnterior));
        }
    }
}
