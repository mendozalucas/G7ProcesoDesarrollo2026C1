package com.escrims.infrastructure.notifications.observers;

import com.escrims.domain.observer.DomainEventBus;
import com.escrims.domain.repository.BusquedaFavoritaRepository;
import com.escrims.domain.repository.ScrimRepository;

/** Alias de compatibilidad con el nombre previo al refactor. */
public class SearchAlertObserver extends AlertaBusquedaObserver {

    public SearchAlertObserver(BusquedaFavoritaRepository busquedaRepository,
                               ScrimRepository scrimRepository,
                               DomainEventBus eventBus) {
        super(busquedaRepository, scrimRepository, eventBus);
    }
}
