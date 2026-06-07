package com.escrims.infrastructure.persistence.jpa.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "reportes_conducta")
public class ReporteConductaEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID scrimId;

    @Column(nullable = false)
    private UUID reportanteId;

    @Column(nullable = false)
    private UUID reportadoId;

    @Column(nullable = false)
    private String motivo;

    private String estadoResolucion;
    private String sancionTipo;
    private LocalDateTime fechaReporte;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getScrimId() { return scrimId; }
    public void setScrimId(UUID scrimId) { this.scrimId = scrimId; }
    public UUID getReportanteId() { return reportanteId; }
    public void setReportanteId(UUID reportanteId) { this.reportanteId = reportanteId; }
    public UUID getReportadoId() { return reportadoId; }
    public void setReportadoId(UUID reportadoId) { this.reportadoId = reportadoId; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public String getEstadoResolucion() { return estadoResolucion; }
    public void setEstadoResolucion(String estadoResolucion) { this.estadoResolucion = estadoResolucion; }
    public String getSancionTipo() { return sancionTipo; }
    public void setSancionTipo(String sancionTipo) { this.sancionTipo = sancionTipo; }
    public LocalDateTime getFechaReporte() { return fechaReporte; }
    public void setFechaReporte(LocalDateTime fechaReporte) { this.fechaReporte = fechaReporte; }
}
