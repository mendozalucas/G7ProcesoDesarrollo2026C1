package com.escrims.domain.model.scrim;



import com.escrims.domain.events.CanceladoEvent;

import com.escrims.domain.events.FinalizadoEvent;

import com.escrims.domain.events.LobbyArmadoEvent;

import com.escrims.domain.model.usuario.Jugador;

import com.escrims.support.TestFixtures;

import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.junit.jupiter.api.Assertions.assertTrue;



class ScrimTest {



    @Test

    void estadoInicial_esBuscandoJugadores() {

        Jugador org = TestFixtures.jugador("org");

        Scrim scrim = TestFixtures.scrim(org);



        assertEquals("BUSCANDO", scrim.getEstadoNombre());

        assertTrue(scrim.getLobby() != null);

    }



    @Test

    void avanzarEstado_recorreCicloDeVidaCompleto() {

        Jugador org = TestFixtures.jugador("org");

        Scrim scrim = TestFixtures.scrim(org);



        scrim.avanzarEstado();

        assertEquals("LOBBY_ARMADO", scrim.getEstadoNombre());

        assertInstanceOf(LobbyArmadoEvent.class, scrim.recolectarEventos().get(0));



        scrim.avanzarEstado();

        assertEquals("CONFIRMADO", scrim.getEstadoNombre());



        scrim.avanzarEstado();

        assertEquals("EN_JUEGO", scrim.getEstadoNombre());



        scrim.avanzarEstado();

        assertEquals("FINALIZADO", scrim.getEstadoNombre());
        var eventos = scrim.recolectarEventos();
        assertInstanceOf(FinalizadoEvent.class, eventos.get(eventos.size() - 1));

    }



    @Test

    void cancelar_desdeBuscando_pasaACancelado() {

        Jugador org = TestFixtures.jugador("org");

        Scrim scrim = TestFixtures.scrim(org);



        scrim.cancelar("sin jugadores");



        assertEquals("CANCELADO", scrim.getEstadoNombre());

        assertEquals("sin jugadores", scrim.getMotivoCancelacion());

        assertInstanceOf(CanceladoEvent.class, scrim.recolectarEventos().get(0));

    }



    @Test

    void cancelar_desdeEnJuego_lanzaExcepcion() {

        Jugador org = TestFixtures.jugador("org");

        Scrim scrim = TestFixtures.scrim(org);

        scrim.avanzarEstado();

        scrim.avanzarEstado();

        scrim.avanzarEstado();



        assertThrows(IllegalStateException.class, () -> scrim.cancelar("motivo"));

    }



    @Test

    void avanzar_desdeFinalizado_lanzaExcepcion() {

        Jugador org = TestFixtures.jugador("org");

        Scrim scrim = TestFixtures.scrim(org);

        for (int i = 0; i < 4; i++) {

            scrim.avanzarEstado();

        }



        assertThrows(IllegalStateException.class, scrim::avanzarEstado);

    }

}


