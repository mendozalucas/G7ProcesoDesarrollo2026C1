package com.escrims.domain.model.usuario.factory;

import com.escrims.domain.model.usuario.Jugador;
import com.escrims.domain.model.usuario.Moderador;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class UsuarioFactoryRegistryTest {

    private final UsuarioFactoryRegistry registry = new UsuarioFactoryRegistry(
            new FactoryJugador(), new FactoryModerador());

    @Test
    void crear_jugadorPorDefecto() {
        var usuario = registry.crear(UUID.randomUUID(), "j", "j@test.local", "hash", "JUGADOR");
        assertInstanceOf(Jugador.class, usuario);
    }

    @Test
    void crear_moderadorCuandoRolEsMod() {
        var usuario = registry.crear(UUID.randomUUID(), "m", "m@test.local", "hash", "MOD");
        assertInstanceOf(Moderador.class, usuario);
    }
}
