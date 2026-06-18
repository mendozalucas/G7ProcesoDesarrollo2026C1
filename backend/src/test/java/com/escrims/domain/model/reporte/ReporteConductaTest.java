package com.escrims.domain.model.reporte;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReporteConductaTest {

    @Test
    void resolver_marcaComoResuelto() {
        ReporteConducta reporte = new ReporteConducta(1L, "motivo");

        assertTrue(reporte.estaPendiente());

        reporte.resolver();

        assertFalse(reporte.estaPendiente());
        assertTrue(reporte.isResuelto());
    }
}
