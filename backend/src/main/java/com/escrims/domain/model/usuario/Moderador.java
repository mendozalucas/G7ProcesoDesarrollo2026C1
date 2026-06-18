package com.escrims.domain.model.usuario;

import com.escrims.domain.model.reporte.ReporteConducta;

import java.util.UUID;

public class Moderador extends Usuario {

    public Moderador(UUID id, String username, String email, String passwordHash) {
        super(id, username, email, passwordHash);
    }

    @Override
    public String getTipoUsuario() {
        return "MODERADOR";
    }

    public void revisarReporte(ReporteConducta reporte) {
        // Derivado a revisión humana; la resolución concreta la hace aprobar/rechazar.
    }

    public void aprobarReporte(ReporteConducta reporte) {
        reporte.resolver();
    }

    public void rechazarReporte(ReporteConducta reporte) {
        rechazar(reporte);
    }

    public void rechazar(ReporteConducta reporte) {
        // Permanece pendiente.
    }
}
