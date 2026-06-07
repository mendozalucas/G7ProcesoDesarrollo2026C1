package com.escrims.domain.events;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Clase base de eventos de dominio.
 * El tipo de evento se determina por la clase concreta (polimorfismo), no por un enum.
 */
public abstract class DomainEvent {

    private final UUID eventId;
    private final LocalDateTime occurredOn;

    protected DomainEvent() {
        this.eventId = UUID.randomUUID();
        this.occurredOn = LocalDateTime.now();
    }

    public UUID getEventId() { return eventId; }
    public LocalDateTime getOccurredOn() { return occurredOn; }
}
