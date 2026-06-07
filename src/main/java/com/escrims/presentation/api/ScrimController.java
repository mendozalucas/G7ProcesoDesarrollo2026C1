package com.escrims.presentation.api;

import com.escrims.application.dto.CreateScrimDTO;
import com.escrims.application.dto.EstadisticaDTO;
import com.escrims.application.usecases.*;
import com.escrims.domain.valueobjects.RolJuego;

import java.util.List;
import java.util.UUID;

/**
 * Controlador REST para el recurso Scrim.
 * En Spring Boot se anotaría con @RestController y @RequestMapping("/api/scrims").
 */
public class ScrimController {

    private final CreateScrimUseCase createScrimUseCase;
    private final ApplyToScrimUseCase applyToScrimUseCase;
    private final ConfirmParticipationUseCase confirmParticipationUseCase;
    private final CancelScrimUseCase cancelScrimUseCase;
    private final FinalizeScrimUseCase finalizeScrimUseCase;

    public ScrimController(CreateScrimUseCase createScrimUseCase,
                            ApplyToScrimUseCase applyToScrimUseCase,
                            ConfirmParticipationUseCase confirmParticipationUseCase,
                            CancelScrimUseCase cancelScrimUseCase,
                            FinalizeScrimUseCase finalizeScrimUseCase) {
        this.createScrimUseCase = createScrimUseCase;
        this.applyToScrimUseCase = applyToScrimUseCase;
        this.confirmParticipationUseCase = confirmParticipationUseCase;
        this.cancelScrimUseCase = cancelScrimUseCase;
        this.finalizeScrimUseCase = finalizeScrimUseCase;
    }

    // POST /api/scrims
    public UUID crearScrim(CreateScrimDTO dto) {
        return createScrimUseCase.execute(dto);
    }

    // POST /api/scrims/{id}/postulaciones
    public UUID postularse(UUID scrimId, UUID usuarioId, String juego, String rol) {
        return applyToScrimUseCase.execute(usuarioId, scrimId, new RolJuego(juego, rol));
    }

    // POST /api/scrims/{id}/confirmaciones
    public void confirmar(UUID scrimId, UUID usuarioId) {
        confirmParticipationUseCase.execute(usuarioId, scrimId);
    }

    // POST /api/scrims/{id}/cancelar
    public void cancelar(UUID scrimId, String motivo) {
        cancelScrimUseCase.execute(scrimId, motivo);
    }

    // POST /api/scrims/{id}/finalizar
    public void finalizar(UUID scrimId, List<EstadisticaDTO> estadisticas) {
        finalizeScrimUseCase.execute(scrimId, estadisticas);
    }
}
