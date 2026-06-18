package com.escrims.domain.model.usuario;

import com.escrims.domain.model.reporte.ReporteConducta;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ModeradorTest {

    @Test
    void tipoUsuario_esModerador() {
        Moderador mod = new Moderador(
                java.util.UUID.randomUUID(), "mod", "mod@test.local", "hash");
        assertEquals("MODERADOR", mod.getTipoUsuario());
    }

    @Test
    void aprobarReporte_loResuelve() {
        Moderador mod = new Moderador(
                java.util.UUID.randomUUID(), "mod", "mod@test.local", "hash");
        ReporteConducta reporte = new ReporteConducta(1L, "motivo");

        mod.aprobarReporte(reporte);

        assertTrue(reporte.isResuelto());
    }

    @Test
    void rechazarReporte_permancePendiente() {
        Moderador mod = new Moderador(
                java.util.UUID.randomUUID(), "mod", "mod@test.local", "hash");
        ReporteConducta reporte = new ReporteConducta(1L, "motivo");

        mod.rechazarReporte(reporte);

        assertTrue(reporte.estaPendiente());
    }
}
