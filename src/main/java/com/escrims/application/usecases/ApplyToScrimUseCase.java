package com.escrims.application.usecases;

import com.escrims.domain.services.ParticipationService;
import com.escrims.domain.valueobjects.RolJuego;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApplyToScrimUseCase {

    private final ParticipationService participationService;

    public ApplyToScrimUseCase(ParticipationService participationService) {
        this.participationService = participationService;
    }

    public UUID execute(UUID usuarioId, UUID scrimId, RolJuego rolDeseado) {
        return participationService.postularseAScrim(usuarioId, scrimId, rolDeseado);
    }
}
