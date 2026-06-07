package com.escrims.domain.moderation;

import com.escrims.domain.model.reporte.ReporteConducta;
import com.escrims.domain.strategy.sancion.WarningSancion;

import java.util.Set;

public class AutoModerationHandler extends ModerationHandler {

    private static final Set<String> PALABRAS_CLAVE = Set.of("spam", "bot", "flood", "cheat");

    @Override
    protected void doHandle(ReporteConducta reporte) {
        boolean esObvio = PALABRAS_CLAVE.stream()
                .anyMatch(p -> reporte.getMotivo().toLowerCase().contains(p));
        if (esObvio) {
            reporte.asignarSancion(new WarningSancion());
            reporte.resolver("RESUELTO_AUTO");
        } else {
            pasarAlSiguiente(reporte);
        }
    }
}
