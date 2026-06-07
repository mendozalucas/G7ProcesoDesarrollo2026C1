package com.escrims.presentation.api.dto;

import java.util.UUID;

public class ReporteRequest {

    private UUID scrimId;
    private UUID reportanteId;
    private UUID reportadoId;
    private String motivo;

    public UUID getScrimId() { return scrimId; }
    public void setScrimId(UUID scrimId) { this.scrimId = scrimId; }
    public UUID getReportanteId() { return reportanteId; }
    public void setReportanteId(UUID reportanteId) { this.reportanteId = reportanteId; }
    public UUID getReportadoId() { return reportadoId; }
    public void setReportadoId(UUID reportadoId) { this.reportadoId = reportadoId; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
}
