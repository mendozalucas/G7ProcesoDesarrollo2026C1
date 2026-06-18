package com.escrims.domain.services;



import com.escrims.domain.events.FinalizadoEvent;

import com.escrims.domain.events.LobbyArmadoEvent;

import com.escrims.domain.model.scrim.Scrim;

import com.escrims.domain.model.usuario.Jugador;

import com.escrims.domain.observer.DomainEventBus;

import com.escrims.domain.observer.IObserver;

import com.escrims.support.TestFixtures;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;



import java.util.ArrayList;

import java.util.List;



import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;



class ScrimLifecycleServiceTest {



    private ScrimLifecycleService service;

    private List<com.escrims.domain.events.DomainEvent> eventos;



    @BeforeEach

    void setUp() {

        eventos = new ArrayList<>();

        DomainEventBus bus = new DomainEventBus();

        bus.suscribe((IObserver) eventos::add);

        service = new ScrimLifecycleService(bus);

    }



    @Test

    void iniciar_publicaEventoDeLobbyArmado() {

        Jugador org = TestFixtures.jugador("org");

        Scrim scrim = TestFixtures.scrim(org);



        service.iniciar(scrim);



        assertEquals("LOBBY_ARMADO", scrim.getEstadoNombre());

        assertInstanceOf(LobbyArmadoEvent.class, eventos.get(0));

    }



    @Test

    void finalizar_publicaEventoFinalizado() {

        Jugador org = TestFixtures.jugador("org");

        Scrim scrim = TestFixtures.scrim(org);

        scrim.avanzarEstado();
        scrim.avanzarEstado();
        scrim.avanzarEstado();
        scrim.recolectarEventos();
        eventos.clear();

        service.finalizar(scrim);



        assertEquals("FINALIZADO", scrim.getEstadoNombre());

        assertInstanceOf(FinalizadoEvent.class, eventos.get(0));

    }



    @Test

    void cancelar_publicaEventoCancelado() {

        Jugador org = TestFixtures.jugador("org");

        Scrim scrim = TestFixtures.scrim(org);



        service.cancelar(scrim, "motivo");



        assertEquals("CANCELADO", scrim.getEstadoNombre());

    }

}


