package com.escrims.domain.moderation;

import com.escrims.domain.events.ReporteConductaRegistradoEvent;
import com.escrims.domain.model.reporte.ReporteConducta;
import com.escrims.domain.observer.DomainEventBus;

import java.util.UUID;

public class HumanModerationHandler extends ModerationHandler {

    private final DomainEventBus eventBus;
    private final Moderator moderator;

    public HumanModerationHandler(DomainEventBus eventBus, Moderator moderator) {
        this.eventBus = eventBus;
        this.moderator = moderator;
    }

    @Override
    public void handle(ReporteConducta reporte) {
        eventBus.publish(new ReporteConductaRegistradoEvent(UUID.randomUUID(), null));
    }

    public Moderator getModerator() { return moderator; }
}
