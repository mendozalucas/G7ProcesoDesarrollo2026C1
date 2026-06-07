package com.escrims.domain.events;

import java.util.List;
import java.util.UUID;

public class NuevoScrimDisponibleEvent extends DomainEvent {

    private final UUID scrimId;
    private final List<UUID> usuariosANotificar;

    public NuevoScrimDisponibleEvent(UUID scrimId, List<UUID> usuariosANotificar) {
        super();
        this.scrimId = scrimId;
        this.usuariosANotificar = List.copyOf(usuariosANotificar);
    }

    public UUID getScrimId() { return scrimId; }
    public List<UUID> getUsuariosANotificar() { return usuariosANotificar; }
}
