package com.escrims.domain.model.scrim;

import com.escrims.domain.valueobjects.RolJuego;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Component del patrón Composite (ver Ejemplos/CompositeExercise).
 */
public interface JugadorComponent {

    UUID getUsuarioId();

    RolJuego getRolAsignado();

    List<JugadorComponent> getHijos();

    void agregar(JugadorComponent componente);

    void quitar(UUID usuarioId);

    int contarJugadores();
}
