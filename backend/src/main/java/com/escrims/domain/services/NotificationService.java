package com.escrims.domain.services;

import com.escrims.domain.events.DomainEvent;
import com.escrims.domain.observer.DomainEventBus;

public class NotificationService {

    private final DomainEventBus eventBus;

    public NotificationService(DomainEventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void notificar(DomainEvent event) {
        eventBus.publish(event);
    }
}
