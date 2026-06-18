package com.escrims.presentation.api;

import com.escrims.application.usecases.ModerateReportUseCase;
import com.escrims.presentation.api.dto.ReporteRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/reportes")
public class ModerationController {

    private final ModerateReportUseCase moderateReportUseCase;

    public ModerationController(ModerateReportUseCase moderateReportUseCase) {
        this.moderateReportUseCase = moderateReportUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Long> reportar(@RequestBody ReporteRequest request) {
        Long id = moderateReportUseCase.execute(request.getMotivo());
        return Map.of("reporteId", id);
    }
}
