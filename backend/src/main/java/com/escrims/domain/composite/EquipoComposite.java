package com.escrims.domain.composite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Equipo compuesto: agrupa participantes y expone operaciones sobre el grupo.
 */
public class EquipoComposite implements JugadorComponent {

    private final String nombre;
    private final List<JugadorComponent> miembros = new ArrayList<>();

    public EquipoComposite(String nombre) {
        this.nombre = nombre;
    }

    public void agregar(JugadorComponent miembro) {
        miembros.add(miembro);
    }

    public void quitar(UUID usuarioId) {
        miembros.removeIf(m -> m.getUsuarioId().equals(usuarioId));
    }

    public List<JugadorComponent> getMiembros() {
        return Collections.unmodifiableList(miembros);
    }

    public void intercambiarLados(JugadorComponent a, JugadorComponent b) {
        String ladoA = a.getLado();
        a.asignarLado(b.getLado());
        b.asignarLado(ladoA);
    }

    @Override
    public UUID getUsuarioId() {
        return null;
    }

    @Override
    public String getRolNombre() {
        return nombre;
    }

    @Override
    public String getLado() {
        return "EQUIPO";
    }

    @Override
    public void asignarLado(String lado) {
        miembros.forEach(m -> m.asignarLado(lado));
    }

    @Override
    public void asignarRol(String rolNombre) {
        // No aplica al contenedor.
    }

    public String getNombre() {
        return nombre;
    }
}
