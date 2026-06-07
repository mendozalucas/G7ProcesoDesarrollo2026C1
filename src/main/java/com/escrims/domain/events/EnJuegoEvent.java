package com.escrims.domain.events;

import java.util.UUID;

public class EnJuegoEvent extends DomainEvent {

    private final UUID scrimId;

    public EnJuegoEvent(UUID scrimId) {
        super();
        this.scrimId = scrimId;
    }

    public UUID getScrimId() { return scrimId; }

    @Override
    public void aceptar(DomainEventVisitor visitor) {
        visitor.visit(this);
    }
}
