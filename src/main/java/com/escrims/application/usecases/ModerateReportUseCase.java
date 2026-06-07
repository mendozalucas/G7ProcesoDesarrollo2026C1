package com.escrims.application.usecases;

import com.escrims.domain.model.reporte.ReporteConducta;
import com.escrims.domain.repository.ReporteConductaRepository;
import com.escrims.domain.services.ModerationService;

import java.util.UUID;

public class ModerateReportUseCase {

    private final ModerationService moderationService;
    private final ReporteConductaRepository reporteRepository;

    public ModerateReportUseCase(ModerationService moderationService,
                                   ReporteConductaRepository reporteRepository) {
        this.moderationService = moderationService;
        this.reporteRepository = reporteRepository;
    }

    public UUID execute(UUID scrimId, UUID reportanteId, UUID reportadoId, String motivo) {
        ReporteConducta reporte = new ReporteConducta(scrimId, reportanteId, reportadoId, motivo);
        reporteRepository.save(reporte);
        moderationService.procesarReporte(reporte);
        reporteRepository.save(reporte);
        return reporte.getId();
    }
}
