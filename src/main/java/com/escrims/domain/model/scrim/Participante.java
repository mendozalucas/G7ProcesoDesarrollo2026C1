package com.escrims.domain.model.scrim;

import com.escrims.domain.valueobjects.RolJuego;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/** Leaf del patrón Composite. */
public final class Participante implements JugadorComponent {

    private final UUID usuarioId;
    private final RolJuego rolAsignado;

    public Participante(UUID usuarioId, RolJuego rolAsignado) {
        this.usuarioId = usuarioId;
        this.rolAsignado = rolAsignado;
    }

    public Participante conRol(RolJuego nuevoRol) {
        return new Participante(usuarioId, nuevoRol);
    }

    @Override
    public List<JugadorComponent> getHijos() {
        return Collections.emptyList();
    }

    @Override
    public void agregar(JugadorComponent componente) {
        throw new UnsupportedOperationException("Un participante no puede contener hijos");
    }

    @Override
    public void quitar(UUID id) {
        throw new UnsupportedOperationException("Un participante no puede quitar hijos");
    }

    @Override
    public int contarJugadores() {
        return 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Participante)) return false;
        return Objects.equals(usuarioId, ((Participante) o).usuarioId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuarioId);
    }

    @Override
    public UUID getUsuarioId() { return usuarioId; }

    @Override
    public RolJuego getRolAsignado() { return rolAsignado; }
}
