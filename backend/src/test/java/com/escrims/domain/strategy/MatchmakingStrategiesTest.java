package com.escrims.domain.strategy;



import com.escrims.domain.model.usuario.Jugador;

import com.escrims.domain.model.usuario.Usuario;

import com.escrims.domain.valueobjects.CandidatoMatchmaking;

import com.escrims.domain.valueobjects.FormatoScrim;

import com.escrims.domain.valueobjects.MatchmakingContext;

import com.escrims.support.TestFixtures;

import org.junit.jupiter.api.Test;



import java.util.List;



import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.junit.jupiter.api.Assertions.assertTrue;



class MatchmakingStrategiesTest {



    @Test

    void byMmr_filtraPorRangoYRespetaCupo() {

        Jugador org = TestFixtures.jugador("org");

        var scrim = TestFixtures.scrim(org, new FormatoScrim(2));

        Jugador j1 = TestFixtures.jugador("j1");

        Jugador j2 = TestFixtures.jugador("j2");

        Jugador j3 = TestFixtures.jugador("j3");

        List<CandidatoMatchmaking> candidatos = List.of(

                TestFixtures.candidato(j1, 1600, 50, "Duelist"),

                TestFixtures.candidato(j2, 1700, 40, "Initiator"),

                TestFixtures.candidato(j3, 3000, 30, "Sentinel"));

        MatchmakingContext context = TestFixtures.matchmakingContext(scrim, candidatos);



        List<Usuario> seleccionados = new ByMMRStrategy(200).seleccionar(context);



        assertEquals(2, seleccionados.size());

        assertTrue(seleccionados.stream().noneMatch(u -> u.getId().equals(j3.getId())));

    }



    @Test

    void byMmr_sinCandidatosEnRango_lanzaExcepcion() {

        Jugador org = TestFixtures.jugador("org");

        var scrim = TestFixtures.scrim(org);

        Jugador j1 = TestFixtures.jugador("j1");

        MatchmakingContext context = TestFixtures.matchmakingContext(scrim,

                List.of(TestFixtures.candidato(j1, 50, 30, "Duelist")));



        assertThrows(IllegalStateException.class, () -> new ByMMRStrategy(200).seleccionar(context));

    }



    @Test

    void byLatency_priorizaMenorPing() {

        Jugador org = TestFixtures.jugador("org");

        var scrim = TestFixtures.scrim(org, new FormatoScrim(1));

        Jugador j1 = TestFixtures.jugador("j1");

        Jugador j2 = TestFixtures.jugador("j2");

        MatchmakingContext context = TestFixtures.matchmakingContext(scrim, List.of(

                TestFixtures.candidato(j1, 1600, 70, "Duelist"),

                TestFixtures.candidato(j2, 1600, 20, "Initiator")));



        List<Usuario> seleccionados = new ByLatencyStrategy(80).seleccionar(context);



        assertEquals(2, seleccionados.size());

        assertEquals(j2.getId(), seleccionados.get(0).getId());

    }



    @Test

    void byHistory_favoreceDiversidadDeRoles() {

        Jugador org = TestFixtures.jugador("org");

        var scrim = TestFixtures.scrim(org, new FormatoScrim(1));

        Jugador j1 = TestFixtures.jugador("j1");

        Jugador j2 = TestFixtures.jugador("j2");

        Jugador j3 = TestFixtures.jugador("j3");

        MatchmakingContext context = TestFixtures.matchmakingContext(scrim, List.of(

                TestFixtures.candidato(j1, 1600, 30, "Duelist", 0, 2.0),

                TestFixtures.candidato(j2, 1650, 35, "Duelist", 0, 2.5),

                TestFixtures.candidato(j3, 1700, 40, "Initiator", 0, 1.8)));



        List<Usuario> seleccionados = new ByHistoryStrategy(0.4, 0.3, 0.3).seleccionar(context);



        assertEquals(2, seleccionados.size());

        assertTrue(seleccionados.stream().anyMatch(u -> u.getId().equals(j3.getId())));

    }



    @Test

    void matchmakingSupport_calculaCentroYAnchoMmr() {

        Jugador org = TestFixtures.jugador("org");

        var scrim = TestFixtures.scrim(org);



        assertEquals(1750, MatchmakingSupport.mmrCentro(scrim));

        assertEquals(500, MatchmakingSupport.anchoRangoMmr(scrim));

    }

}


