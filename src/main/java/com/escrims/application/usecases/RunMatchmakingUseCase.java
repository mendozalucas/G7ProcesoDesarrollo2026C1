package com.escrims.application.usecases;

import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.model.usuario.Usuario;
import com.escrims.domain.observer.DomainEventBus;
import com.escrims.domain.repository.ScrimRepository;
import com.escrims.domain.services.MatchmakingService;
import com.escrims.domain.valueobjects.LadoEquipo;
import com.escrims.domain.valueobjects.RolJuego;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RunMatchmakingUseCase {

    private final MatchmakingService matchmakingService;
    private final ScrimRepository scrimRepository;
    private final DomainEventBus eventBus;

    public RunMatchmakingUseCase(MatchmakingService matchmakingService,
                                  ScrimRepository scrimRepository,
                                  DomainEventBus eventBus) {
        this.matchmakingService = matchmakingService;
        this.scrimRepository = scrimRepository;
        this.eventBus = eventBus;
    }

    public void execute(UUID scrimId) {
        Scrim scrim = scrimRepository.findById(scrimId)
                .orElseThrow(() -> new IllegalArgumentException("Scrim no encontrado: " + scrimId));

        if (!"BUSCANDO".equalsIgnoreCase(scrim.getEstadoNombre())) {
            throw new IllegalStateException("Matchmaking solo disponible mientras se buscan jugadores");
        }

        matchmakingService.cambiarEstrategiaPorJuego(scrim.getNombreJuego());
        List<Usuario> candidatos = matchmakingService.ejecutarMatchmaking(scrimId);

        LadoEquipo[] lados = {LadoEquipo.EQUIPO_A, LadoEquipo.EQUIPO_B};
        int ladoIdx = 0;

        for (Usuario usuario : candidatos) {
            if (scrim.getCuposDisponibles() <= 0) {
                break;
            }
            RolJuego rol = resolverRol(usuario, scrim.getNombreJuego());
            scrim.agregarJugador(usuario.getId(), lados[ladoIdx % 2], rol);
            ladoIdx++;
        }

        scrimRepository.save(scrim);
        eventBus.publicarTodos(scrim.recolectarEventos());
    }

    private RolJuego resolverRol(Usuario usuario, String juego) {
        return usuario.getRolesPreferidos().stream()
                .filter(r -> r.getJuego().equalsIgnoreCase(juego))
                .findFirst()
                .orElse(new RolJuego(juego, "FLEX"));
    }
}
