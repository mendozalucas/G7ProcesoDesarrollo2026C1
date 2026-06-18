package com.escrims.domain.model.usuario;

import com.escrims.domain.facade.LobbyFacadePort;
import com.escrims.domain.facade.ModeracionFacadePort;
import com.escrims.domain.facade.ScrimFacadePort;
import com.escrims.domain.model.juego.JuegoFactory;
import com.escrims.domain.model.postulacion.EstadoPostulacion;
import com.escrims.domain.model.reporte.ReporteConducta;
import com.escrims.domain.model.rol.Rol;
import com.escrims.domain.model.scrim.Confirmacion;
import com.escrims.domain.model.scrim.CreateScrimRequest;
import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.valueobjects.Region;
import com.escrims.support.TestFixtures;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class JugadorTest {

    @Test
    void postular_sinFacade_creaPostulacionPendiente() {
        Jugador org = TestFixtures.jugador("org");
        Jugador postulante = TestFixtures.jugador("post");
        Scrim scrim = TestFixtures.scrim(org);
        Rol rol = new Rol(null, "Duelist");

        var postulacion = postulante.postular(scrim, rol);

        assertEquals(EstadoPostulacion.PENDIENTE, postulacion.getEstado());
    }

    @Test
    void postular_conFacade_delega() {
        Jugador postulante = TestFixtures.jugador("post");
        ScrimFacadePort facade = mock(ScrimFacadePort.class);
        Scrim scrim = TestFixtures.scrim(TestFixtures.jugador("org"));
        Rol rol = new Rol(null, "Duelist");
        postulante.conectarFacades(facade, mock(LobbyFacadePort.class), mock(ModeracionFacadePort.class));
        when(facade.postularse(postulante, scrim, rol))
                .thenReturn(new com.escrims.domain.model.postulacion.Postulacion(
                        1L, postulante, scrim, rol, EstadoPostulacion.PENDIENTE));

        postulante.postular(scrim, rol);

        verify(facade).postularse(postulante, scrim, rol);
    }

    @Test
    void crearScrim_sinFacade_lanzaExcepcion() {
        Jugador org = TestFixtures.jugador("org");
        CreateScrimRequest request = new CreateScrimRequest();

        assertThrows(IllegalStateException.class, () -> org.crearScrim(request));
    }

    @Test
    void confirmar_sinFacade_devuelveConfirmacion() {
        Jugador jugador = TestFixtures.jugador("j");
        Scrim scrim = TestFixtures.scrim(TestFixtures.jugador("org"));

        Confirmacion confirmacion = jugador.confirmar(scrim);

        assertTrue(confirmacion.isConfirmado());
    }

    @Test
    void reportarJugador_sinFacade_creaReporte() {
        Jugador reportante = TestFixtures.jugador("a");
        Jugador reportado = TestFixtures.jugador("b");

        ReporteConducta reporte = reportante.reportarJugador(reportado, "tóxico");

        assertEquals("tóxico", reporte.getMotivo());
    }

    @Test
    void perfilJuego_agregaRolesPreferidos() {
        Jugador jugador = TestFixtures.jugador("j");
        PerfilJuego perfil = new PerfilJuego(
                JuegoFactory.para("valorant"),
                new Region(null, "LAN"),
                1500);
        perfil.agregarRolPreferido(new Rol(null, "Duelist"));
        jugador.setPerfilJuego(perfil);

        assertEquals("valorant", jugador.getPerfilJuego().getJuegoPrincipal().getNombre());
        assertEquals(1, jugador.getPerfilJuego().getRolesPreferidos().size());
    }
}
