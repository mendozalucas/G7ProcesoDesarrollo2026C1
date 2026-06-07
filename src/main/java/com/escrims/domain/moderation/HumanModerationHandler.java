package com.escrims.domain.moderation;

import com.escrims.domain.events.ReporteConductaRegistradoEvent;
import com.escrims.domain.model.reporte.ReporteConducta;
import com.escrims.domain.observer.DomainEventBus;

public class HumanModerationHandler extends ModerationHandler {

    private final DomainEventBus eventBus;

    public HumanModerationHandler(DomainEventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    protected void doHandle(ReporteConducta reporte) {
        reporte.resolver("ESCALADO_HUMANO");
        eventBus.publicar(new ReporteConductaRegistradoEvent(reporte.getId(), reporte.getReportadoId()));
    }
}
