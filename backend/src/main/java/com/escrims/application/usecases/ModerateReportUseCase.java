package com.escrims.application.usecases;

import com.escrims.domain.model.reporte.ReporteConducta;
import com.escrims.domain.repository.ReporteConductaRepository;
import com.escrims.domain.services.ModerationService;
import org.springframework.stereotype.Service;

@Service
public class ModerateReportUseCase {

    private final ModerationService moderationService;
    private final ReporteConductaRepository reporteRepository;

    public ModerateReportUseCase(ModerationService moderationService,
                                 ReporteConductaRepository reporteRepository) {
        this.moderationService = moderationService;
        this.reporteRepository = reporteRepository;
    }

    public Long execute(String motivo) {
        ReporteConducta reporte = new ReporteConducta(null, motivo);
        ReporteConducta guardado = reporteRepository.save(reporte);
        moderationService.procesarReporte(guardado);
        reporteRepository.save(guardado);
        return guardado.getId();
    }
}
