package com.escrims.domain.model.reporte;

import java.time.LocalDateTime;

public class ReporteConducta {

    private Long id;
    private String motivo;
    private LocalDateTime fecha;
    private boolean resuelto;

    public ReporteConducta(Long id, String motivo) {
        this.id = id;
        this.motivo = motivo;
        this.fecha = LocalDateTime.now();
        this.resuelto = false;
    }

    public void resolver() {
        this.resuelto = true;
    }

    public boolean estaPendiente() {
        return !resuelto;
    }

    public Long getId() { return id; }
    public String getMotivo() { return motivo; }
    public LocalDateTime getFecha() { return fecha; }
    public boolean isResuelto() { return resuelto; }
}
