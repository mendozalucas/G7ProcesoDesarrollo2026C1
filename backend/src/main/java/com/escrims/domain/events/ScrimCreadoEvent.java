package com.escrims.domain.events;

import com.escrims.domain.valueobjects.Region;

import java.util.UUID;

public class ScrimCreadoEvent extends DomainEvent {

    private final UUID scrimId;
    private final String juego;
    private final Region region;

    public ScrimCreadoEvent(UUID scrimId, String juego, Region region) {
        super();
        this.scrimId = scrimId;
        this.juego = juego;
        this.region = region;
    }

    public UUID getScrimId() { return scrimId; }
    public String getJuego() { return juego; }
    public Region getRegion() { return region; }

    @Override
    public void aceptar(DomainEventVisitor visitor) {
        visitor.visit(this);
    }
}
