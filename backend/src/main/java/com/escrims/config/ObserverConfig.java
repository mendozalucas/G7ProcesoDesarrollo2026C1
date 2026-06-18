package com.escrims.config;

import com.escrims.domain.observer.DomainEventBus;
import com.escrims.domain.repository.BusquedaFavoritaRepository;
import com.escrims.domain.repository.ScrimRepository;
import com.escrims.infrastructure.notifications.factory.INotifierFactory;
import com.escrims.infrastructure.notifications.observers.AlertaBusquedaObserver;
import com.escrims.infrastructure.notifications.observers.NotificationObserver;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObserverConfig {

    private final DomainEventBus eventBus;
    private final INotifierFactory notifierFactory;
    private final BusquedaFavoritaRepository busquedaRepository;
    private final ScrimRepository scrimRepository;

    public ObserverConfig(DomainEventBus eventBus,
                          INotifierFactory notifierFactory,
                          BusquedaFavoritaRepository busquedaRepository,
                          ScrimRepository scrimRepository) {
        this.eventBus = eventBus;
        this.notifierFactory = notifierFactory;
        this.busquedaRepository = busquedaRepository;
        this.scrimRepository = scrimRepository;
    }

    @PostConstruct
    void registrarObservers() {
        eventBus.suscribir(new NotificationObserver(notifierFactory));
        eventBus.suscribir(new AlertaBusquedaObserver(busquedaRepository, scrimRepository, eventBus));
    }
}
