package com.escrims.domain.services;

import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.observer.DomainEventBus;

public class ScrimLifecycleService {

    private final DomainEventBus eventBus;

    public ScrimLifecycleService(DomainEventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void iniciar(Scrim scrim) {
        scrim.avanzarEstado();
        scrim.recolectarEventos().forEach(eventBus::publish);
    }

    public void finalizar(Scrim scrim) {
        scrim.avanzarEstado();
        scrim.recolectarEventos().forEach(eventBus::publish);
    }

    public void cancelar(Scrim scrim, String motivo) {
        scrim.cancelar(motivo);
        scrim.recolectarEventos().forEach(eventBus::publish);
    }
}
