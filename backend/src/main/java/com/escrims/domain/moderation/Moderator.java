package com.escrims.domain.moderation;

import com.escrims.domain.model.reporte.ReporteConducta;

public class Moderator {

    private Long id;
    private String nombre;
    private String email;

    public Moderator(Long id, String nombre, String email) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
    }

    public void revisarReporte(ReporteConducta reporte) {}

    public void aprobarReporte(ReporteConducta reporte) {
        reporte.resolver();
    }

    public void rechazarReporte(ReporteConducta reporte) {
        // permanece pendiente
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
}
