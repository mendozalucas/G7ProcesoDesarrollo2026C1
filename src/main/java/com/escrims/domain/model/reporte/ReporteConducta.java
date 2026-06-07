package com.escrims.domain.model.reporte;

import com.escrims.domain.strategy.sancion.SancionEstrategia;
import com.escrims.domain.strategy.sancion.SancionFactory;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Aggregate Root.
 * Reemplaza los enums EstadoReporte y SancionTipo con polimorfismo:
 * - SancionEstrategia: Strategy que sabe cómo aplicarse sobre un Usuario
 * - El estado del reporte se rastrea como String del handler que lo resolvió
 */
public class ReporteConducta {

    private final UUID id;
    private final UUID scrimId;
    private final UUID reportanteId;
    private final UUID reportadoId;
    private final String motivo;
    private String estadoResolucion;
    private SancionEstrategia sancion;
    private final LocalDateTime fechaReporte;

    public ReporteConducta(UUID scrimId, UUID reportanteId, UUID reportadoId, String motivo) {
        this(UUID.randomUUID(), scrimId, reportanteId, reportadoId, motivo,
                "PENDIENTE", null, LocalDateTime.now());
    }

    private ReporteConducta(UUID id, UUID scrimId, UUID reportanteId, UUID reportadoId,
                            String motivo, String estadoResolucion, SancionEstrategia sancion,
                            LocalDateTime fechaReporte) {
        this.id = id;
        this.scrimId = scrimId;
        this.reportanteId = reportanteId;
        this.reportadoId = reportadoId;
        this.motivo = motivo;
        this.estadoResolucion = estadoResolucion;
        this.sancion = sancion;
        this.fechaReporte = fechaReporte;
    }

    public void asignarSancion(SancionEstrategia sancion) {
        this.sancion = sancion;
    }

    public void resolver(String estado) {
        this.estadoResolucion = estado;
    }

    public void escalar() {
        this.estadoResolucion = "ESCALADO_HUMANO";
    }

    public boolean estaPendiente() {
        return "PENDIENTE".equals(estadoResolucion);
    }

    public UUID getId() { return id; }
    public UUID getScrimId() { return scrimId; }
    public UUID getReportanteId() { return reportanteId; }
    public UUID getReportadoId() { return reportadoId; }
    public String getMotivo() { return motivo; }
    public String getEstadoResolucion() { return estadoResolucion; }
    public SancionEstrategia getSancion() { return sancion; }
    public LocalDateTime getFechaReporte() { return fechaReporte; }

    public static ReporteConducta reconstituir(UUID id, UUID scrimId, UUID reportanteId,
                                                UUID reportadoId, String motivo,
                                                String estadoResolucion, String sancionTipo,
                                                LocalDateTime fechaReporte) {
        return new ReporteConducta(id, scrimId, reportanteId, reportadoId, motivo,
                estadoResolucion, SancionFactory.para(sancionTipo), fechaReporte);
    }
}
