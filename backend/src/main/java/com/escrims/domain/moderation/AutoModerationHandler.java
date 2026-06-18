package com.escrims.domain.moderation;

import com.escrims.domain.model.reporte.ReporteConducta;

import java.util.Set;

public class AutoModerationHandler extends ModerationHandler {

    private static final Set<String> PALABRAS_CLAVE = Set.of("spam", "bot", "flood", "cheat");

    @Override
    public void handle(ReporteConducta reporte) {
        boolean esObvio = PALABRAS_CLAVE.stream()
                .anyMatch(p -> reporte.getMotivo().toLowerCase().contains(p));
        if (esObvio) {
            reporte.resolver();
        } else {
            pasarAlSiguiente(reporte);
        }
    }
}
