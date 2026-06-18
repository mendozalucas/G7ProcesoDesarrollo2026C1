package com.escrims.domain.builder;

import com.escrims.domain.model.juego.JuegoFactory;
import com.escrims.domain.model.usuario.Jugador;
import com.escrims.domain.valueobjects.FormatoScrim;
import com.escrims.domain.valueobjects.Rango;
import com.escrims.domain.valueobjects.Region;
import com.escrims.support.TestFixtures;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ScrimBuilderTest {

    @Test
    void build_creaScrimConDatosCompletos() {
        Jugador organizador = TestFixtures.jugador("org");

        var scrim = new ScrimBuilder()
                .conJuego(JuegoFactory.para("valorant"))
                .conFormato(new FormatoScrim(5))
                .conModalidad("RANKED_LIKE")
                .conRegion(new Region(null, "LAN/AR"))
                .conRango(new Rango(null, "Gold", 1000), new Rango(null, "Plat", 2000))
                .conLatenciaMax(50)
                .conFechaHora(LocalDateTime.of(2026, 6, 20, 18, 0))
                .creadoPor(organizador)
                .build();

        assertNotNull(scrim.getId());
        assertEquals("valorant", scrim.getJuego().getNombre());
        assertEquals(5, scrim.getFormato().getJugadoresPorLado());
        assertEquals("RANKED_LIKE", scrim.getModalidad());
        assertEquals(organizador, scrim.getOrganizador());
        assertNotNull(scrim.getLobby());
        assertEquals("BUSCANDO", scrim.getEstadoNombre());
    }

    @Test
    void build_sinJuego_lanzaExcepcion() {
        Jugador organizador = TestFixtures.jugador("org");

        assertThrows(IllegalStateException.class, () -> new ScrimBuilder()
                .conFormato(new FormatoScrim(5))
                .conRegion(new Region(null, "LAN"))
                .conRango(new Rango(null, "Gold", 1000), new Rango(null, "Plat", 2000))
                .conFechaHora(LocalDateTime.now())
                .creadoPor(organizador)
                .build());
    }

    @Test
    void build_sinFormato_lanzaExcepcion() {
        Jugador organizador = TestFixtures.jugador("org");

        assertThrows(IllegalStateException.class, () -> new ScrimBuilder()
                .conJuego(JuegoFactory.para("lol"))
                .conRegion(new Region(null, "LAN"))
                .conRango(new Rango(null, "Gold", 1000), new Rango(null, "Plat", 2000))
                .conFechaHora(LocalDateTime.now())
                .creadoPor(organizador)
                .build());
    }
}
