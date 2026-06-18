package com.escrims.domain.model.postulacion;

import com.escrims.domain.model.rol.Rol;
import com.escrims.domain.model.usuario.Jugador;
import com.escrims.support.TestFixtures;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PostulacionTest {

    @Test
    void aceptar_transicionaDePendienteAAceptada() {
        Jugador organizador = TestFixtures.jugador("org");
        Jugador postulante = TestFixtures.jugador("postulante");
        var scrim = TestFixtures.scrim(organizador);
        var postulacion = new Postulacion(1L, postulante, scrim, new Rol(null, "Duelist"), EstadoPostulacion.PENDIENTE);

        assertTrue(postulacion.estaPendiente());

        postulacion.aceptar();

        assertTrue(postulacion.estaAceptada());
        assertEquals(EstadoPostulacion.ACEPTADA, postulacion.getEstado());
    }

    @Test
    void rechazar_transicionaDePendienteARechazada() {
        Jugador organizador = TestFixtures.jugador("org");
        Jugador postulante = TestFixtures.jugador("postulante");
        var scrim = TestFixtures.scrim(organizador);
        var postulacion = new Postulacion(1L, postulante, scrim, new Rol(null, "Duelist"), EstadoPostulacion.PENDIENTE);

        postulacion.rechazar();

        assertFalse(postulacion.estaAceptada());
        assertEquals(EstadoPostulacion.RECHAZADA, postulacion.getEstado());
    }

    @Test
    void aceptar_desdeAceptada_lanzaExcepcion() {
        Jugador organizador = TestFixtures.jugador("org");
        Jugador postulante = TestFixtures.jugador("postulante");
        var scrim = TestFixtures.scrim(organizador);
        var postulacion = new Postulacion(1L, postulante, scrim, new Rol(null, "Duelist"), EstadoPostulacion.ACEPTADA);

        assertThrows(IllegalStateException.class, postulacion::aceptar);
    }

    @Test
    void rechazar_desdeRechazada_lanzaExcepcion() {
        Jugador organizador = TestFixtures.jugador("org");
        Jugador postulante = TestFixtures.jugador("postulante");
        var scrim = TestFixtures.scrim(organizador);
        var postulacion = new Postulacion(1L, postulante, scrim, new Rol(null, "Duelist"), EstadoPostulacion.RECHAZADA);

        assertThrows(IllegalStateException.class, postulacion::rechazar);
    }
}
