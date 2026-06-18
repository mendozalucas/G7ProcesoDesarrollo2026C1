package com.escrims.application.facade;

import com.escrims.domain.facade.LobbyFacadePort;
import com.escrims.application.usecases.RunMatchmakingUseCase;
import com.escrims.domain.command.CommandHistoryInvoker;
import com.escrims.domain.command.LobbyCommand;
import com.escrims.domain.events.LobbyArmadoEvent;
import com.escrims.domain.model.lobby.Lobby;
import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.repository.ScrimRepository;
import com.escrims.domain.services.NotificationService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LobbyFacade implements LobbyFacadePort {

    private final ScrimRepository scrimRepository;
    private final NotificationService notificationService;
    private final RunMatchmakingUseCase runMatchmakingUseCase;
    private final Map<UUID, CommandHistoryInvoker> historialPorScrim = new ConcurrentHashMap<>();

    public LobbyFacade(ScrimRepository scrimRepository,
                       NotificationService notificationService,
                       RunMatchmakingUseCase runMatchmakingUseCase) {
        this.scrimRepository = scrimRepository;
        this.notificationService = notificationService;
        this.runMatchmakingUseCase = runMatchmakingUseCase;
    }

    @Override
    public void ejecutarComando(LobbyCommand comando, Lobby lobby) {
        historial(lobby.getScrimId()).ejecutar(comando, lobby.getGestorLobby());
        Scrim scrim = cargarScrim(lobby.getScrimId());
        scrimRepository.save(scrim);
        notificationService.notificar(new LobbyArmadoEvent(scrim.getId(), lobby.getParticipantes()));
    }

    @Override
    public void deshacerComando(Lobby lobby) {
        historial(lobby.getScrimId()).deshacer(lobby.getGestorLobby());
        scrimRepository.save(cargarScrim(lobby.getScrimId()));
    }

    @Override
    public void armarLobby(Lobby lobby) {
        runMatchmakingUseCase.execute(lobby.getScrimId());
        Scrim scrim = cargarScrim(lobby.getScrimId());
        notificationService.notificar(new LobbyArmadoEvent(scrim.getId(), scrim.getParticipantesLobby()));
    }

    @Override
    public Lobby lobbyDesdeScrim(UUID scrimId) {
        return cargarScrim(scrimId).getLobby();
    }

    private CommandHistoryInvoker historial(UUID scrimId) {
        return historialPorScrim.computeIfAbsent(scrimId, id -> new CommandHistoryInvoker());
    }

    private Scrim cargarScrim(UUID scrimId) {
        return scrimRepository.findById(scrimId)
                .orElseThrow(() -> new IllegalArgumentException("Scrim no encontrado: " + scrimId));
    }
}
