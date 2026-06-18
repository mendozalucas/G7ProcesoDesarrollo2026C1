package com.escrims.domain.model.scrim;

import com.escrims.domain.model.usuario.Jugador;
import com.escrims.support.TestFixtures;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class EquipoTest {

    @Test
    void agregarJugador_incrementaCupos() {
        Equipo equipo = new Equipo(1L, "Equipo A");
        Jugador j1 = TestFixtures.jugador("j1");

        equipo.agregarJugador(j1);

        assertEquals(1, equipo.getCuposOcupados());
        assertEquals(1, equipo.getJugadores().size());
    }

    @Test
    void agregarJugador_noDuplica() {
        Equipo equipo = new Equipo(1L, "Equipo A");
        Jugador j1 = TestFixtures.jugador("j1");

        equipo.agregarJugador(j1);
        equipo.agregarJugador(j1);

        assertEquals(1, equipo.getCuposOcupados());
    }

    @Test
    void quitarJugador_loEliminaDelEquipo() {
        Equipo equipo = new Equipo(1L, "Equipo A");
        Jugador j1 = TestFixtures.jugador("j1");
        equipo.agregarJugador(j1);

        equipo.quitarJugador(j1);

        assertEquals(0, equipo.getCuposOcupados());
        assertFalse(equipo.getJugadores().contains(j1));
    }
}
