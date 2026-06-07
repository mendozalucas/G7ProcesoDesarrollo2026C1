package com.escrims.presentation.api;

import com.escrims.application.usecases.ModerateReportUseCase;

import java.util.UUID;

/**
 * Controlador REST para moderación.
 * En Spring Boot: @RestController @RequestMapping("/api/reportes").
 */
public class ModerationController {

    private final ModerateReportUseCase moderateReportUseCase;

    public ModerationController(ModerateReportUseCase moderateReportUseCase) {
        this.moderateReportUseCase = moderateReportUseCase;
    }

    // POST /api/reportes
    public UUID reportar(UUID scrimId, UUID reportanteId, UUID reportadoId, String motivo) {
        return moderateReportUseCase.execute(scrimId, reportanteId, reportadoId, motivo);
    }
}
