package com.escrims.presentation.api;

import com.escrims.application.dto.EstadisticaDTO;
import com.escrims.application.dto.PostulacionResponseDTO;
import com.escrims.application.dto.ScrimResponseDTO;
import com.escrims.application.usecases.*;
import com.escrims.presentation.api.dto.*;
import com.escrims.presentation.api.mapper.ScrimRequestMapper;
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
    private final ListPostulacionesByScrimUseCase listPostulacionesByScrimUseCase;

    public ScrimController(CreateScrimUseCase createScrimUseCase,
                           SearchScrimsUseCase searchScrimsUseCase,
                           GetScrimUseCase getScrimUseCase,
                           ApplyToScrimUseCase applyToScrimUseCase,
                           AcceptPostulacionUseCase acceptPostulacionUseCase,
                           ConfirmParticipationUseCase confirmParticipationUseCase,
                           CancelScrimUseCase cancelScrimUseCase,
                           FinalizeScrimUseCase finalizeScrimUseCase,
                           RunMatchmakingUseCase runMatchmakingUseCase,
                           ListPostulacionesByScrimUseCase listPostulacionesByScrimUseCase) {
        this.createScrimUseCase = createScrimUseCase;
        this.searchScrimsUseCase = searchScrimsUseCase;
        this.getScrimUseCase = getScrimUseCase;
        this.applyToScrimUseCase = applyToScrimUseCase;
        this.acceptPostulacionUseCase = acceptPostulacionUseCase;
        this.confirmParticipationUseCase = confirmParticipationUseCase;
        this.cancelScrimUseCase = cancelScrimUseCase;
        this.finalizeScrimUseCase = finalizeScrimUseCase;
        this.runMatchmakingUseCase = runMatchmakingUseCase;
        this.listPostulacionesByScrimUseCase = listPostulacionesByScrimUseCase;
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
    public Map<String, UUID> crear(@RequestBody CreateScrimRequest request) {
        return Map.of("id", createScrimUseCase.execute(ScrimRequestMapper.toCommand(request)));
    }

    @GetMapping("/{id}/postulaciones")
    public List<PostulacionResponseDTO> listarPostulaciones(@PathVariable UUID id) {
        return listPostulacionesByScrimUseCase.execute(id);
    }

    @PostMapping("/{id}/postulaciones")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Long> postularse(@PathVariable UUID id, @RequestBody PostulacionRequest request) {
        Long postulacionId = applyToScrimUseCase.execute(request.getUsuarioId(), id, request.getRol());
        return Map.of("postulacionId", postulacionId);
    }

    @PostMapping("/{id}/postulaciones/aceptar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void aceptarPostulacion(@PathVariable UUID id, @RequestBody AceptarPostulacionRequest request) {
        acceptPostulacionUseCase.execute(request.getPostulacionId());
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
