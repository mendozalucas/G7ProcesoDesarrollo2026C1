package com.escrims.domain.model.lobby;

import com.escrims.domain.model.rol.Rol;
import com.escrims.domain.model.usuario.Jugador;
import com.escrims.support.TestFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GestorLobbyTest {

    private GestorLobby gestorLobby;
    private Jugador j1;
    private Jugador j2;
    private Jugador j3;

    @BeforeEach
    void setUp() {
        gestorLobby = new GestorLobby();
        j1 = TestFixtures.jugador("j1");
        j2 = TestFixtures.jugador("j2");
        j3 = TestFixtures.jugador("j3");
    }

    @Test
    void balancearEquipos_distribuyeEntreEquipoAyB() {
        gestorLobby.balancearEquipos(j1);
        gestorLobby.balancearEquipos(j2);

        assertEquals(1, gestorLobby.getEquipoA().getCuposOcupados());
        assertEquals(1, gestorLobby.getEquipoB().getCuposOcupados());
        assertTrue(gestorLobby.contieneJugador(j1));
        assertTrue(gestorLobby.contieneJugador(j2));
    }

    @Test
    void balancearEquipos_noDuplicaJugadorExistente() {
        gestorLobby.balancearEquipos(j1);

        gestorLobby.balancearEquipos(j1);

        assertEquals(1, gestorLobby.getEquipoA().getCuposOcupados() + gestorLobby.getEquipoB().getCuposOcupados());
    }

    @Test
    void swapJugadores_intercambiaEntreEquipos() {
        gestorLobby.balancearEquipos(j1);
        gestorLobby.balancearEquipos(j2);

        boolean j1EnA = gestorLobby.getEquipoA().getJugadores().contains(j1);
        gestorLobby.swapJugadores(j1, j2);

        assertEquals(!j1EnA, gestorLobby.getEquipoA().getJugadores().contains(j1));
        assertEquals(j1EnA, gestorLobby.getEquipoA().getJugadores().contains(j2));
    }

    @Test
    void asignarRol_guardaRolPorJugador() {
        Rol duelist = new Rol(null, "Duelist");
        gestorLobby.asignarRol(j1, duelist);

        assertEquals(duelist, gestorLobby.getRolDe(j1));
    }

    @Test
    void quitarJugador_loEliminaDeAmbosEquiposYRol() {
        gestorLobby.balancearEquipos(j1);
        gestorLobby.asignarRol(j1, new Rol(null, "Flex"));

        gestorLobby.quitarJugador(j1);

        assertFalse(gestorLobby.contieneJugador(j1));
        assertEquals(null, gestorLobby.getRolDe(j1));
    }

    @Test
    void invitarJugador_delegaEnBalancearEquipos() {
        gestorLobby.invitarJugador(j1);
        gestorLobby.invitarJugador(j2);
        gestorLobby.invitarJugador(j3);

        assertEquals(2, gestorLobby.getEquipoA().getCuposOcupados());
        assertEquals(1, gestorLobby.getEquipoB().getCuposOcupados());
    }
}
