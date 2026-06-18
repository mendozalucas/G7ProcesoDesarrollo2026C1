package com.escrims.domain.model.lobby;

import com.escrims.domain.model.postulacion.EstadoPostulacion;
import com.escrims.domain.model.rol.Rol;
import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.model.usuario.Jugador;
import com.escrims.domain.valueobjects.FormatoScrim;
import com.escrims.support.TestFixtures;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LobbyTest {

    @Test
    void agregarPostulacion_registraPostulacionPendiente() {
        Jugador organizador = TestFixtures.jugador("org");
        Scrim scrim = TestFixtures.scrim(organizador);
        Lobby lobby = new Lobby(scrim.getId(), scrim.getFormato());
        Jugador postulante = TestFixtures.jugador("postulante");

        var postulacion = lobby.agregarPostulacion(postulante, new Rol(null, "Duelist"), scrim);

        assertEquals(1, lobby.getPostulaciones().size());
        assertEquals(EstadoPostulacion.PENDIENTE, postulacion.getEstado());
        assertEquals(postulante, postulacion.getJugador());
    }

    @Test
    void cupoCompleto_cuandoSeAlcanzaElFormato() {
        UUID scrimId = UUID.randomUUID();
        Lobby lobby = new Lobby(scrimId, new FormatoScrim(2));
        Jugador j1 = TestFixtures.jugador("j1");
        Jugador j2 = TestFixtures.jugador("j2");
        Jugador j3 = TestFixtures.jugador("j3");
        Jugador j4 = TestFixtures.jugador("j4");

        lobby.getGestorLobby().balancearEquipos(j1);
        lobby.getGestorLobby().balancearEquipos(j2);
        assertFalse(lobby.cupoCompleto());

        lobby.getGestorLobby().balancearEquipos(j3);
        lobby.getGestorLobby().balancearEquipos(j4);
        assertTrue(lobby.cupoCompleto());
    }

    @Test
    void todosConfirmados_requiereTodasLasConfirmacionesEnTrue() {
        Jugador organizador = TestFixtures.jugador("org");
        Scrim scrim = TestFixtures.scrim(organizador);
        Lobby lobby = scrim.getLobby();
        Jugador j1 = TestFixtures.jugador("j1");
        Jugador j2 = TestFixtures.jugador("j2");
        lobby.getGestorLobby().balancearEquipos(j1);
        lobby.getGestorLobby().balancearEquipos(j2);

        assertFalse(lobby.todosConfirmados());

        lobby.inicializarConfirmaciones(scrim);
        assertFalse(lobby.todosConfirmados());

        lobby.getConfirmaciones().get(0).confirmar();
        assertFalse(lobby.todosConfirmados());

        lobby.getConfirmaciones().forEach(c -> c.confirmar());
        assertTrue(lobby.todosConfirmados());
    }

    @Test
    void confirmarParticipacion_registraConfirmacionYAvanzaCuandoCorresponde() {
        Jugador organizador = TestFixtures.jugador("org");
        Scrim scrim = TestFixtures.scrim(organizador);
        scrim.avanzarEstado();
        Lobby lobby = scrim.getLobby();
        Jugador j1 = TestFixtures.jugador("j1");
        lobby.getGestorLobby().balancearEquipos(j1);
        lobby.inicializarConfirmaciones(scrim);

        lobby.confirmarParticipacion(j1, scrim);

        assertTrue(lobby.getConfirmaciones().get(0).isConfirmado());
        assertTrue(lobby.todosConfirmados());
    }

    @Test
    void confirmarParticipacion_rechazaJugadorFueraDelLobby() {
        Jugador organizador = TestFixtures.jugador("org");
        Scrim scrim = TestFixtures.scrim(organizador);
        scrim.avanzarEstado();
        Jugador externo = TestFixtures.jugador("externo");

        assertThrows(IllegalStateException.class,
                () -> scrim.getLobby().confirmarParticipacion(externo, scrim));
    }

    @Test
    void getParticipantes_devuelveIdsDeJugadoresEnEquipos() {
        UUID scrimId = UUID.randomUUID();
        Lobby lobby = new Lobby(scrimId, new FormatoScrim(5));
        Jugador j1 = TestFixtures.jugador("j1");
        Jugador j2 = TestFixtures.jugador("j2");
        lobby.getGestorLobby().balancearEquipos(j1);
        lobby.getGestorLobby().balancearEquipos(j2);

        assertEquals(2, lobby.getParticipantes().size());
        assertTrue(lobby.getParticipantes().contains(j1.getId()));
        assertTrue(lobby.getParticipantes().contains(j2.getId()));
    }
}
