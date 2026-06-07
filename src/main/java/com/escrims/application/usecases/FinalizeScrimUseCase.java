package com.escrims.application.usecases;

import com.escrims.application.dto.EstadisticaDTO;
import com.escrims.domain.services.ScrimLifecycleService;
import com.escrims.domain.services.StatisticsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FinalizeScrimUseCase {

    private final ScrimLifecycleService lifecycleService;
    private final StatisticsService statisticsService;

    public FinalizeScrimUseCase(ScrimLifecycleService lifecycleService,
                                  StatisticsService statisticsService) {
        this.lifecycleService = lifecycleService;
        this.statisticsService = statisticsService;
    }

    public void execute(UUID scrimId, List<EstadisticaDTO> estadisticas) {
        lifecycleService.finalizar(scrimId);
        estadisticas.forEach(dto ->
                statisticsService.cargarEstadistica(
                        scrimId,
                        dto.getUsuarioId(),
                        dto.getKills(),
                        dto.getDeaths(),
                        dto.getAssists(),
                        dto.isEsMvp(),
                        dto.getObservaciones()
                )
        );
    }
}
