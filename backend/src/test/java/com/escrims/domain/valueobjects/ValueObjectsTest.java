package com.escrims.domain.valueobjects;



import com.escrims.domain.model.juego.JuegoFactory;

import com.escrims.domain.model.usuario.Jugador;

import com.escrims.support.TestFixtures;

import org.junit.jupiter.api.Test;



import java.time.DayOfWeek;

import java.time.LocalDateTime;

import java.time.LocalTime;



import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertFalse;

import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.junit.jupiter.api.Assertions.assertTrue;



class ValueObjectsTest {



    @Test

    void formatoScrim_rechazaJugadoresInvalidos() {

        assertThrows(IllegalArgumentException.class, () -> new FormatoScrim(0));

    }



    @Test

    void formatoScrim_calculaTotalJugadores() {

        assertEquals(10, new FormatoScrim(5).getTotalJugadores());

    }



    @Test

    void rangosPermitidos_validaOrdenYContencion() {

        Rango min = new Rango(null, "Gold", 1500);

        Rango max = new Rango(null, "Plat", 2000);

        RangosPermitidos rangos = new RangosPermitidos(min, max);



        assertTrue(rangos.contiene(new Rango(null, "Gold2", 1750)));

        assertFalse(rangos.contiene(new Rango(null, "Bronze", 1000)));

        assertThrows(IllegalArgumentException.class,

                () -> new RangosPermitidos(max, min));

    }



    @Test

    void latencia_rechazaPingNegativo() {

        assertThrows(IllegalArgumentException.class, () -> new Latencia(-1));

    }



    @Test

    void latencia_estaEnUmbral() {

        Latencia actual = new Latencia(50);

        Latencia max = new Latencia(80);

        assertTrue(actual.estaEnUmbral(max));

        assertFalse(new Latencia(90).estaEnUmbral(max));

    }



    @Test

    void disponibilidadHoraria_validaRangoYCobertura() {

        DisponibilidadHoraria disp = new DisponibilidadHoraria(

                DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(22, 0));



        assertTrue(disp.cubre(DayOfWeek.MONDAY, LocalTime.of(20, 0)));

        assertFalse(disp.cubre(DayOfWeek.TUESDAY, LocalTime.of(20, 0)));

        assertThrows(IllegalArgumentException.class, () -> new DisponibilidadHoraria(

                DayOfWeek.MONDAY, LocalTime.of(22, 0), LocalTime.of(18, 0)));

    }



    @Test

    void criteriosBusqueda_filtraPorJuegoRegionYFecha() {

        Jugador org = TestFixtures.jugador("org");

        var scrim = TestFixtures.scrim(org);

        LocalDateTime fecha = scrim.getFechaHora();



        CriteriosBusqueda criterios = new CriteriosBusqueda(

                "valorant",

                null,

                null,

                new Region(null, "LAN/AR"),

                fecha.minusHours(1),

                fecha.plusHours(1),

                new Latencia(100));



        assertTrue(criterios.coincideCon(scrim));

        CriteriosBusqueda otraRegion = new CriteriosBusqueda(
                "valorant", null, null, new Region(null, "NA/EAST"), null, null, null);
        assertFalse(otraRegion.coincideCon(scrim));

        CriteriosBusqueda otroJuego = new CriteriosBusqueda(
                "lol", null, null, null, null, null, null);
        assertFalse(otroJuego.coincideCon(scrim));

    }



    @Test

    void candidatoMatchmaking_normalizaStrikesYRol() {

        Jugador j = TestFixtures.jugador("j");

        CandidatoMatchmaking c = new CandidatoMatchmaking(j, 1500, 40, null, -2, 1.0);

        assertEquals(0, c.getStrikes());

        assertEquals("", c.getRolNombre());

    }



    @Test

    void juegoFactory_soportaJuegosConocidos() {

        assertEquals("valorant", JuegoFactory.para("valorant").getNombre());

        assertEquals("lol", JuegoFactory.para("LOL").getNombre());

        assertThrows(IllegalArgumentException.class, () -> JuegoFactory.para("minecraft"));

        assertThrows(IllegalArgumentException.class, () -> JuegoFactory.para(null));

    }

}


