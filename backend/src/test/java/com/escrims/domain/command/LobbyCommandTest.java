package com.escrims.domain.command;

import com.escrims.domain.model.lobby.GestorLobby;
import com.escrims.domain.model.rol.Rol;
import com.escrims.domain.model.usuario.Jugador;
import com.escrims.support.TestFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LobbyCommandTest {

    private GestorLobby gestorLobby;
    private Jugador j1;
    private Jugador j2;

    @BeforeEach
    void setUp() {
        gestorLobby = new GestorLobby();
        j1 = TestFixtures.jugador("j1");
        j2 = TestFixtures.jugador("j2");
    }

    @Test
    void asignarRolCommand_asignaYDeshace() {
        var comando = new AsignarRolCommand(j1, "Duelist");

        comando.ejecutar(gestorLobby);
        assertEquals("Duelist", gestorLobby.getRolDe(j1).getNombre());

        comando.deshacer(gestorLobby);
        assertNull(gestorLobby.getRolDe(j1));
    }

    @Test
    void asignarRolCommand_deshacerRestauraRolAnterior() {
        gestorLobby.asignarRol(j1, new Rol(null, "Initiator"));
        var comando = new AsignarRolCommand(j1, "Duelist");

        comando.ejecutar(gestorLobby);
        assertEquals("Duelist", gestorLobby.getRolDe(j1).getNombre());

        comando.deshacer(gestorLobby);
        assertEquals("Initiator", gestorLobby.getRolDe(j1).getNombre());
    }

    @Test
    void swapJugadoresCommand_intercambiaYDeshace() {
        gestorLobby.balancearEquipos(j1);
        gestorLobby.balancearEquipos(j2);
        boolean j1EnA = gestorLobby.getEquipoA().getJugadores().contains(j1);

        var comando = new SwapJugadoresCommand(j1, j2);
        comando.ejecutar(gestorLobby);
        assertEquals(!j1EnA, gestorLobby.getEquipoA().getJugadores().contains(j1));

        comando.deshacer(gestorLobby);
        assertEquals(j1EnA, gestorLobby.getEquipoA().getJugadores().contains(j1));
    }

    @Test
    void invitarJugadoresCommand_invitaYDeshace() {
        var comando = new InvitarJugadoresCommand(j1);

        comando.ejecutar(gestorLobby);
        assertTrue(gestorLobby.contieneJugador(j1));

        comando.deshacer(gestorLobby);
        assertFalse(gestorLobby.contieneJugador(j1));
    }

    @Test
    void commandHistoryInvoker_ejecutaYDeshaceEnOrdenLifo() {
        var invoker = new CommandHistoryInvoker();
        gestorLobby.balancearEquipos(j1);

        invoker.ejecutar(new AsignarRolCommand(j1, "Duelist"), gestorLobby);
        invoker.ejecutar(new AsignarRolCommand(j1, "Flex"), gestorLobby);
        assertEquals("Flex", gestorLobby.getRolDe(j1).getNombre());

        invoker.deshacer(gestorLobby);
        assertEquals("Duelist", gestorLobby.getRolDe(j1).getNombre());

        invoker.deshacer(gestorLobby);
        assertNull(gestorLobby.getRolDe(j1));
    }

    @Test
    void commandHistoryInvoker_deshacerSinHistorial_lanzaExcepcion() {
        var invoker = new CommandHistoryInvoker();
        assertThrows(IllegalStateException.class, () -> invoker.deshacer(gestorLobby));
    }
}
