package com.escrims.domain.events;

import java.util.UUID;

public class PostulacionAceptadaEvent extends DomainEvent {

    private final UUID postulacionId;
    private final UUID usuarioId;
    private final UUID scrimId;

    public PostulacionAceptadaEvent(UUID postulacionId, UUID usuarioId, UUID scrimId) {
        super();
        this.postulacionId = postulacionId;
        this.usuarioId = usuarioId;
        this.scrimId = scrimId;
    }

    public UUID getPostulacionId() { return postulacionId; }
    public UUID getUsuarioId() { return usuarioId; }
    public UUID getScrimId() { return scrimId; }
}
