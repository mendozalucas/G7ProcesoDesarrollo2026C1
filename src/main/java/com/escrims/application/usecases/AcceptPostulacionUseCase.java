package com.escrims.application.usecases;

import com.escrims.domain.services.ParticipationService;
import com.escrims.domain.valueobjects.LadoEquipo;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AcceptPostulacionUseCase {

    private final ParticipationService participationService;

    public AcceptPostulacionUseCase(ParticipationService participationService) {
        this.participationService = participationService;
    }

    public void execute(UUID postulacionId, LadoEquipo lado) {
        participationService.aceptarPostulacion(postulacionId, lado);
    }
}
