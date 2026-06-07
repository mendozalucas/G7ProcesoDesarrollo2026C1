package com.escrims.domain.events;

import com.escrims.domain.valueobjects.FormatoScrim;
import com.escrims.domain.valueobjects.Region;

import java.time.LocalDateTime;
import java.util.UUID;

public class ScrimCreadoEvent extends DomainEvent {

    private final UUID scrimId;
    private final String juego;
    private final Region region;
    private final FormatoScrim formato;
    private final LocalDateTime fechaHora;

    public ScrimCreadoEvent(UUID scrimId, String juego, Region region,
                             FormatoScrim formato, LocalDateTime fechaHora) {
        super();
        this.scrimId = scrimId;
        this.juego = juego;
        this.region = region;
        this.formato = formato;
        this.fechaHora = fechaHora;
    }

    public UUID getScrimId() { return scrimId; }
    public String getJuego() { return juego; }
    public Region getRegion() { return region; }
    public FormatoScrim getFormato() { return formato; }
    public LocalDateTime getFechaHora() { return fechaHora; }

    @Override
    public void aceptar(DomainEventVisitor visitor) {
        visitor.visit(this);
    }
}
