package com.escrims.domain.model.scrim;

import com.escrims.domain.model.usuario.Jugador;
import com.escrims.support.TestFixtures;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConfirmacionTest {

    @Test
    void confirmar_marcaComoConfirmado() {
        Jugador org = TestFixtures.jugador("org");
        Jugador jugador = TestFixtures.jugador("j1");
        Scrim scrim = TestFixtures.scrim(org);
        Confirmacion confirmacion = new Confirmacion(1L, jugador, scrim, false);

        assertTrue(confirmacion.esPendiente());

        confirmacion.confirmar();

        assertTrue(confirmacion.isConfirmado());
        assertFalse(confirmacion.esPendiente());
    }

    @Test
    void declinar_marcaComoNoConfirmado() {
        Jugador org = TestFixtures.jugador("org");
        Jugador jugador = TestFixtures.jugador("j1");
        Scrim scrim = TestFixtures.scrim(org);
        Confirmacion confirmacion = new Confirmacion(1L, jugador, scrim, true);

        confirmacion.declinar();

        assertFalse(confirmacion.isConfirmado());
        assertTrue(confirmacion.esPendiente());
    }
}
