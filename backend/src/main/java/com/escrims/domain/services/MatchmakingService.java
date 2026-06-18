package com.escrims.domain.services;

import com.escrims.domain.strategy.IMatchmakingStrategy;

public class MatchmakingService {

    private IMatchmakingStrategy estrategia;

    public MatchmakingService(IMatchmakingStrategy estrategia) {
        this.estrategia = estrategia;
    }

    public void setEstrategia(IMatchmakingStrategy estrategia) {
        this.estrategia = estrategia;
    }

    public IMatchmakingStrategy getEstrategia() { return estrategia; }
}
