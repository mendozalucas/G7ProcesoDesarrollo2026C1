package com.escrims.application.usecases;

import com.escrims.domain.model.postulacion.EstadoPostulacion;
import com.escrims.domain.model.postulacion.Postulacion;
import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.model.usuario.Usuario;
import com.escrims.domain.observer.DomainEventBus;
import com.escrims.domain.repository.PostulacionRepository;
import com.escrims.domain.repository.ScrimRepository;
import com.escrims.domain.services.MatchmakingService;
import com.escrims.domain.valueobjects.MatchmakingContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RunMatchmakingUseCase {

    private final MatchmakingService matchmakingService;
    private final ScrimRepository scrimRepository;
    private final PostulacionRepository postulacionRepository;
    private final DomainEventBus eventBus;

    public RunMatchmakingUseCase(MatchmakingService matchmakingService,
                                 ScrimRepository scrimRepository,
                                 PostulacionRepository postulacionRepository,
                                 DomainEventBus eventBus) {
        this.matchmakingService = matchmakingService;
        this.scrimRepository = scrimRepository;
        this.postulacionRepository = postulacionRepository;
        this.eventBus = eventBus;
    }

    public void execute(UUID scrimId) {
        Scrim scrim = scrimRepository.findById(scrimId)
                .orElseThrow(() -> new IllegalArgumentException("Scrim no encontrado: " + scrimId));

        if (!"BUSCANDO".equalsIgnoreCase(scrim.getEstadoNombre())) {
            throw new IllegalStateException("Matchmaking solo disponible mientras se buscan jugadores");
        }

        MatchmakingContext context = scrimRepository.buildMatchmakingContext(scrim);
        if (context.getCandidatos().isEmpty()) {
            throw new IllegalStateException("No hay jugadores aceptados para armar el lobby");
        }

        List<Usuario> seleccionados = matchmakingService.getEstrategia().seleccionar(context);
        if (seleccionados.isEmpty()) {
            throw new IllegalStateException("La estrategia de matchmaking no seleccionó jugadores");
        }

        Set<UUID> idsSeleccionados = seleccionados.stream()
                .map(Usuario::getId)
                .collect(Collectors.toSet());

        scrim.registrarParticipantesLobby(idsSeleccionados.stream().toList());
        actualizarPostulaciones(scrimId, idsSeleccionados);
        scrim.avanzarEstado();

        scrimRepository.save(scrim);
        scrim.recolectarEventos().forEach(eventBus::publish);
    }

    private void actualizarPostulaciones(UUID scrimId, Set<UUID> idsSeleccionados) {
        for (Postulacion postulacion : postulacionRepository.findByScrimId(scrimId)) {
            if (postulacion.getEstado() == EstadoPostulacion.ACEPTADA) {
                if (!idsSeleccionados.contains(postulacion.getUsuario().getId())) {
                    postulacion.rechazar();
                    postulacionRepository.save(postulacion);
                }
            }
        }
    }
}
