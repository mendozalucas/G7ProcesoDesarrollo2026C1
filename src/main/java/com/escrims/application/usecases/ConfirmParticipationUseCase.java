package com.escrims.application.usecases;

import com.escrims.domain.services.ParticipationService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ConfirmParticipationUseCase {

    private final ParticipationService participationService;

    public ConfirmParticipationUseCase(ParticipationService participationService) {
        this.participationService = participationService;
    }

    public void execute(UUID usuarioId, UUID scrimId) {
        participationService.confirmarParticipacion(usuarioId, scrimId);
    }
}
