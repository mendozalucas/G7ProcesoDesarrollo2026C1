package com.escrims.presentation.api;

import com.escrims.application.dto.CreateScrimDTO;
import com.escrims.application.dto.EstadisticaDTO;
import com.escrims.application.dto.ScrimResponseDTO;
import com.escrims.application.usecases.*;
import com.escrims.domain.valueobjects.LadoEquipo;
import com.escrims.domain.valueobjects.RolJuego;
import com.escrims.presentation.api.dto.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/scrims")
public class ScrimController {

    private final CreateScrimUseCase createScrimUseCase;
    private final SearchScrimsUseCase searchScrimsUseCase;
    private final GetScrimUseCase getScrimUseCase;
    private final ApplyToScrimUseCase applyToScrimUseCase;
    private final AcceptPostulacionUseCase acceptPostulacionUseCase;
    private final ConfirmParticipationUseCase confirmParticipationUseCase;
    private final CancelScrimUseCase cancelScrimUseCase;
    private final FinalizeScrimUseCase finalizeScrimUseCase;
    private final RunMatchmakingUseCase runMatchmakingUseCase;

    public ScrimController(CreateScrimUseCase createScrimUseCase,
                           SearchScrimsUseCase searchScrimsUseCase,
                           GetScrimUseCase getScrimUseCase,
                           ApplyToScrimUseCase applyToScrimUseCase,
                           AcceptPostulacionUseCase acceptPostulacionUseCase,
                           ConfirmParticipationUseCase confirmParticipationUseCase,
                           CancelScrimUseCase cancelScrimUseCase,
                           FinalizeScrimUseCase finalizeScrimUseCase,
                           RunMatchmakingUseCase runMatchmakingUseCase) {
        this.createScrimUseCase = createScrimUseCase;
        this.searchScrimsUseCase = searchScrimsUseCase;
        this.getScrimUseCase = getScrimUseCase;
        this.applyToScrimUseCase = applyToScrimUseCase;
        this.acceptPostulacionUseCase = acceptPostulacionUseCase;
        this.confirmParticipationUseCase = confirmParticipationUseCase;
        this.cancelScrimUseCase = cancelScrimUseCase;
        this.finalizeScrimUseCase = finalizeScrimUseCase;
        this.runMatchmakingUseCase = runMatchmakingUseCase;
    }

    @GetMapping
    public List<ScrimResponseDTO> listar(
            @RequestParam(required = false) String juego,
            @RequestParam(required = false) Integer jugadoresPorLado,
            @RequestParam(required = false) String servidor,
            @RequestParam(required = false) String zona,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta,
            @RequestParam(required = false) Integer latenciaMaxMs) {
        return searchScrimsUseCase.execute(juego, jugadoresPorLado, servidor, zona, desde, hasta, latenciaMaxMs);
    }

    @GetMapping("/{id}")
    public ScrimResponseDTO obtener(@PathVariable UUID id) {
        return getScrimUseCase.execute(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, UUID> crear(@RequestBody CreateScrimDTO dto) {
        return Map.of("id", createScrimUseCase.execute(dto));
    }

    @PostMapping("/{id}/postulaciones")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, UUID> postularse(@PathVariable UUID id, @RequestBody PostulacionRequest request) {
        UUID postulacionId = applyToScrimUseCase.execute(
                request.getUsuarioId(), id, new RolJuego(request.getJuego(), request.getRol()));
        return Map.of("postulacionId", postulacionId);
    }

    @PostMapping("/{id}/postulaciones/aceptar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void aceptarPostulacion(@PathVariable UUID id, @RequestBody AceptarPostulacionRequest request) {
        LadoEquipo lado = "EQUIPO_B".equalsIgnoreCase(request.getLado())
                ? LadoEquipo.EQUIPO_B : LadoEquipo.EQUIPO_A;
        acceptPostulacionUseCase.execute(request.getPostulacionId(), lado);
    }

    @PostMapping("/{id}/confirmaciones")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmar(@PathVariable UUID id, @RequestBody ConfirmacionRequest request) {
        confirmParticipationUseCase.execute(request.getUsuarioId(), id);
    }

    @PostMapping("/{id}/matchmaking")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ejecutarMatchmaking(@PathVariable UUID id) {
        runMatchmakingUseCase.execute(id);
    }

    @PostMapping("/{id}/cancelar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelar(@PathVariable UUID id, @RequestBody CancelarRequest request) {
        cancelScrimUseCase.execute(id, request.getMotivo());
    }

    @PostMapping("/{id}/finalizar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void finalizar(@PathVariable UUID id, @RequestBody List<EstadisticaDTO> estadisticas) {
        finalizeScrimUseCase.execute(id, estadisticas);
    }
}
