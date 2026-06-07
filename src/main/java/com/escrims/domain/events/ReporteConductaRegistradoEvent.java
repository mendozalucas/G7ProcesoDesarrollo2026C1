package com.escrims.domain.events;

import java.util.UUID;

public class ReporteConductaRegistradoEvent extends DomainEvent {

    private final UUID reporteId;
    private final UUID reportadoId;

    public ReporteConductaRegistradoEvent(UUID reporteId, UUID reportadoId) {
        super();
        this.reporteId = reporteId;
        this.reportadoId = reportadoId;
    }

    public UUID getReporteId() { return reporteId; }
    public UUID getReportadoId() { return reportadoId; }
}
