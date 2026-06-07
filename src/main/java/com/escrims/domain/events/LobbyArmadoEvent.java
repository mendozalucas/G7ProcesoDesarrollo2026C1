package com.escrims.domain.events;

import java.util.List;
import java.util.UUID;

public class LobbyArmadoEvent extends DomainEvent {

    private final UUID scrimId;
    private final List<UUID> participantesIds;

    public LobbyArmadoEvent(UUID scrimId, List<UUID> participantesIds) {
        super();
        this.scrimId = scrimId;
        this.participantesIds = List.copyOf(participantesIds);
    }

    public UUID getScrimId() { return scrimId; }
    public List<UUID> getParticipantesIds() { return participantesIds; }
}
