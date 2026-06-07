package com.escrims.domain.services;

import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.model.usuario.Usuario;
import com.escrims.domain.repository.ScrimRepository;
import com.escrims.domain.repository.UsuarioRepository;
import com.escrims.domain.strategy.ByHistoryStrategy;
import com.escrims.domain.strategy.ByLatencyStrategy;
import com.escrims.domain.strategy.ByMMRStrategy;
import com.escrims.domain.strategy.IMatchmakingStrategy;
import com.escrims.domain.strategy.MatchmakingStrategy;

import java.util.List;
import java.util.UUID;

public class MatchmakingService {

    private IMatchmakingStrategy estrategia;
    private final ScrimRepository scrimRepository;
    private final UsuarioRepository usuarioRepository;
    private Scrim scrimActual;

    public MatchmakingService(IMatchmakingStrategy defaultStrategy,
                               ScrimRepository scrimRepository,
                               UsuarioRepository usuarioRepository) {
        this.estrategia = defaultStrategy;
        this.scrimRepository = scrimRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public void setEstrategia(IMatchmakingStrategy estrategia) {
        this.estrategia = estrategia;
    }

    public void setEstrategia(MatchmakingStrategy estrategia) {
        setEstrategia((IMatchmakingStrategy) estrategia);
    }

    public List<Usuario> ejecutarMatchmaking(UUID scrimId) {
        Scrim scrim = scrimRepository.findById(scrimId)
                .orElseThrow(() -> new IllegalArgumentException("Scrim no encontrado: " + scrimId));
        this.scrimActual = scrim;
        List<Usuario> candidatos = usuarioRepository.findCandidatosParaScrim(scrim);
        return estrategia.seleccionar(candidatos);
    }

    public void cambiarEstrategiaPorJuego(String juego) {
        switch (juego.toLowerCase()) {
            case "valorant":
                estrategia = new ByMMRStrategy(300, scrimActual);
                break;
            case "lol":
                estrategia = new ByHistoryStrategy(0.4, 0.3, 0.3, scrimActual);
                break;
            case "cs2":
                estrategia = new ByLatencyStrategy(scrimActual);
                break;
            default:
                break;
        }
    }

    public Scrim getScrimActual() { return scrimActual; }
}
