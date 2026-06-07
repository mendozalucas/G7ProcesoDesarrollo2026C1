package com.escrims.domain.events;

import java.util.UUID;

public class CanceladoEvent extends DomainEvent {

    private final UUID scrimId;
    private final String motivo;

    public CanceladoEvent(UUID scrimId, String motivo) {
        super();
        this.scrimId = scrimId;
        this.motivo = motivo;
    }

    public UUID getScrimId() { return scrimId; }
    public String getMotivo() { return motivo; }
}
