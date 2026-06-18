package com.escrims.domain.moderation;

import com.escrims.domain.events.ReporteConductaRegistradoEvent;
import com.escrims.domain.model.reporte.ReporteConducta;
import com.escrims.domain.model.usuario.Moderador;
import com.escrims.domain.observer.DomainEventBus;
import com.escrims.domain.observer.IObserver;
import com.escrims.support.TestFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ModerationChainTest {

    private List<com.escrims.domain.events.DomainEvent> eventos;
    private ModerationHandler chain;

    @BeforeEach
    void setUp() {
        eventos = new ArrayList<>();
        DomainEventBus bus = new DomainEventBus();
        bus.suscribe((IObserver) eventos::add);
        Moderador moderador = TestFixtures.moderador("mod");
        AutoModerationHandler auto = new AutoModerationHandler();
        BotModerationHandler bot = new BotModerationHandler();
        HumanModerationHandler human = new HumanModerationHandler(bus, moderador);
        auto.setNext(bot).setNext(human);
        chain = auto;
    }

    @Test
    void autoResuelve_conPalabraClaveSpam() {
        ReporteConducta reporte = new ReporteConducta(1L, "usuario hace spam en chat");

        chain.handle(reporte);

        assertFalse(reporte.estaPendiente());
    }

    @Test
    void botResuelve_conNoShowYLargoMotivo() {
        ReporteConducta reporte = new ReporteConducta(1L,
                "El jugador hizo no-show y no avisó con anticipación");

        chain.handle(reporte);

        assertFalse(reporte.estaPendiente());
    }

    @Test
    void humanoEscala_cuandoNoHayAutoResolucion() {
        ReporteConducta reporte = new ReporteConducta(1L, "comportamiento tóxico");

        chain.handle(reporte);

        assertTrue(reporte.estaPendiente());
        assertTrue(eventos.stream().anyMatch(e -> e instanceof ReporteConductaRegistradoEvent));
    }
}
