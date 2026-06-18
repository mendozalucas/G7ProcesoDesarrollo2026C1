package com.escrims.domain.moderation;

import com.escrims.domain.events.ReporteConductaRegistradoEvent;
import com.escrims.domain.model.reporte.ReporteConducta;
import com.escrims.domain.model.usuario.Moderador;
import com.escrims.domain.observer.DomainEventBus;

import java.util.UUID;

public class HumanModerationHandler extends ModerationHandler {

    private final DomainEventBus eventBus;
    private final Moderador moderador;

    public HumanModerationHandler(DomainEventBus eventBus, Moderador moderador) {
        this.eventBus = eventBus;
        this.moderador = moderador;
    }

    @Override
    public void handle(ReporteConducta reporte) {
        moderador.revisarReporte(reporte);
        eventBus.publish(new ReporteConductaRegistradoEvent(UUID.randomUUID(), null));
    }

    public Moderador getModerador() { return moderador; }
}
