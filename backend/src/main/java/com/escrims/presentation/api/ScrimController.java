package com.escrims.presentation.api;

import com.escrims.application.dto.EstadisticaDTO;
import com.escrims.application.dto.PostulacionResponseDTO;
import com.escrims.application.dto.ScrimResponseDTO;
import com.escrims.application.facade.JugadorFacadeBinder;
import com.escrims.application.facade.LobbyFacade;
import com.escrims.application.facade.ModeracionFacade;
import com.escrims.application.facade.ScrimFacade;
import com.escrims.application.usecases.*;
import com.escrims.domain.model.rol.Rol;
import com.escrims.domain.model.usuario.Jugador;
import com.escrims.domain.model.usuario.Usuario;
import com.escrims.domain.repository.ScrimRepository;
import com.escrims.domain.repository.UsuarioRepository;
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

    private final ScrimFacade scrimFacade;
    private final LobbyFacade lobbyFacade;
    private final SearchScrimsUseCase searchScrimsUseCase;
    private final GetScrimUseCase getScrimUseCase;
    private final AcceptPostulacionUseCase acceptPostulacionUseCase;
    private final CancelScrimUseCase cancelScrimUseCase;
    private final ListPostulacionesByScrimUseCase listPostulacionesByScrimUseCase;
    private final UsuarioRepository usuarioRepository;
    private final ScrimRepository scrimRepository;
    private final JugadorFacadeBinder jugadorFacadeBinder;

    public ScrimController(ScrimFacade scrimFacade,
                           LobbyFacade lobbyFacade,
                           SearchScrimsUseCase searchScrimsUseCase,
                           GetScrimUseCase getScrimUseCase,
                           AcceptPostulacionUseCase acceptPostulacionUseCase,
                           CancelScrimUseCase cancelScrimUseCase,
                           ListPostulacionesByScrimUseCase listPostulacionesByScrimUseCase,
                           UsuarioRepository usuarioRepository,
                           ScrimRepository scrimRepository,
                           JugadorFacadeBinder jugadorFacadeBinder) {
        this.scrimFacade = scrimFacade;
        this.lobbyFacade = lobbyFacade;
        this.searchScrimsUseCase = searchScrimsUseCase;
        this.getScrimUseCase = getScrimUseCase;
        this.acceptPostulacionUseCase = acceptPostulacionUseCase;
        this.cancelScrimUseCase = cancelScrimUseCase;
        this.listPostulacionesByScrimUseCase = listPostulacionesByScrimUseCase;
        this.usuarioRepository = usuarioRepository;
        this.scrimRepository = scrimRepository;
        this.jugadorFacadeBinder = jugadorFacadeBinder;
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
        Jugador organizador = requerirJugador(request.getOrganizadorId());
        var scrim = scrimFacade.crearScrim(organizador, ScrimRequestMapper.toCommand(request));
        return Map.of("id", scrim.getId());
    }

    @GetMapping("/{id}/postulaciones")
    public List<PostulacionResponseDTO> listarPostulaciones(@PathVariable UUID id) {
        return listPostulacionesByScrimUseCase.execute(id);
    }

    @PostMapping("/{id}/postulaciones")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Long> postularse(@PathVariable UUID id, @RequestBody PostulacionRequest request) {
        Jugador jugador = requerirJugador(request.getUsuarioId());
        var scrim = scrimRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Scrim no encontrado: " + id));
        var postulacion = scrimFacade.postularse(jugador, scrim, new Rol(null, request.getRol()));
        return Map.of("postulacionId", postulacion.getId());
    }

    @PostMapping("/{id}/postulaciones/aceptar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void aceptarPostulacion(@PathVariable UUID id, @RequestBody AceptarPostulacionRequest request) {
        acceptPostulacionUseCase.execute(request.getPostulacionId());
    }

    @PostMapping("/{id}/confirmaciones")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmar(@PathVariable UUID id, @RequestBody ConfirmacionRequest request) {
        Jugador jugador = requerirJugador(request.getUsuarioId());
        var scrim = scrimRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Scrim no encontrado: " + id));
        scrimFacade.confirmarJugador(jugador, scrim);
    }

    @PostMapping("/{id}/matchmaking")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ejecutarMatchmaking(@PathVariable UUID id) {
        lobbyFacade.armarLobby(lobbyFacade.lobbyDesdeScrim(id));
    }

    @PostMapping("/{id}/cancelar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelar(@PathVariable UUID id, @RequestBody CancelarRequest request) {
        cancelScrimUseCase.execute(id, request.getMotivo());
    }

    @PostMapping("/{id}/finalizar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void finalizar(@PathVariable UUID id, @RequestBody List<EstadisticaDTO> estadisticas) {
        var scrim = scrimRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Scrim no encontrado: " + id));
        Jugador organizador = requerirJugador(scrim.getOrganizador().getId());
        scrimFacade.cargarResultados(organizador, scrim, estadisticas);
    }

    private Jugador requerirJugador(UUID usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + usuarioId));
        return jugadorFacadeBinder.vincular(usuario);
    }
}
