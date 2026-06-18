package com.escrims.domain.composite;

import java.util.UUID;

/**
 * Componente del patrón Composite para jugadores en un equipo/lobby.
 */
public interface JugadorComponent {

    UUID getUsuarioId();

    String getRolNombre();

    String getLado();

    void asignarLado(String lado);

    void asignarRol(String rolNombre);
}
