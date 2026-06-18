package com.escrims.presentation.api;

import com.escrims.application.facade.JugadorFacadeBinder;
import com.escrims.application.facade.ModeracionFacade;
import com.escrims.application.usecases.ModerateReportUseCase;
import com.escrims.domain.model.usuario.Jugador;
import com.escrims.domain.model.usuario.Usuario;
import com.escrims.domain.repository.ScrimRepository;
import com.escrims.domain.repository.UsuarioRepository;
import com.escrims.presentation.api.dto.CalificacionRequest;
import com.escrims.presentation.api.dto.ReporteRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/reportes")
public class ModerationController {

    private final ModeracionFacade moderacionFacade;
    private final ModerateReportUseCase moderateReportUseCase;
    private final UsuarioRepository usuarioRepository;
    private final ScrimRepository scrimRepository;
    private final JugadorFacadeBinder jugadorFacadeBinder;

    public ModerationController(ModeracionFacade moderacionFacade,
                                ModerateReportUseCase moderateReportUseCase,
                                UsuarioRepository usuarioRepository,
                                ScrimRepository scrimRepository,
                                JugadorFacadeBinder jugadorFacadeBinder) {
        this.moderacionFacade = moderacionFacade;
        this.moderateReportUseCase = moderateReportUseCase;
        this.usuarioRepository = usuarioRepository;
        this.scrimRepository = scrimRepository;
        this.jugadorFacadeBinder = jugadorFacadeBinder;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Long> reportar(@RequestBody ReporteRequest request) {
        if (request.getReportanteId() != null && request.getReportadoId() != null) {
            Jugador reportante = requerirJugador(request.getReportanteId());
            Jugador reportado = requerirJugador(request.getReportadoId());
            var reporte = moderacionFacade.reportarJugador(reportante, reportado, request.getMotivo());
            return Map.of("reporteId", reporte.getId());
        }
        Long id = moderateReportUseCase.execute(request.getMotivo());
        return Map.of("reporteId", id);
    }

    @PostMapping("/calificaciones")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Long> calificar(@RequestBody CalificacionRequest request) {
        Jugador calificador = requerirJugador(request.getCalificadorId());
        Jugador calificado = requerirJugador(request.getCalificadoId());
        var scrim = scrimRepository.findById(request.getScrimId())
                .orElseThrow(() -> new IllegalArgumentException("Scrim no encontrado: " + request.getScrimId()));
        var rating = moderacionFacade.calificarJugador(
                calificador, calificado, scrim, request.getPuntuacion(), request.getComentario());
        return Map.of("ratingId", rating.getId());
    }

    private Jugador requerirJugador(UUID usuarioId) {
        if (usuarioId == null) {
            throw new IllegalArgumentException("usuarioId es obligatorio");
        }
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + usuarioId));
        return jugadorFacadeBinder.vincular(usuario);
    }
}
