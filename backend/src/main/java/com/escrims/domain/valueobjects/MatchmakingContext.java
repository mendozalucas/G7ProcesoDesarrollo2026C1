package com.escrims.domain.valueobjects;

import com.escrims.domain.model.scrim.Scrim;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class MatchmakingContext {

    private final Scrim scrim;
    private final int cupoMaximo;
    private final List<CandidatoMatchmaking> candidatos;

    public MatchmakingContext(Scrim scrim, int cupoMaximo, List<CandidatoMatchmaking> candidatos) {
        this.scrim = Objects.requireNonNull(scrim);
        this.cupoMaximo = Math.max(1, cupoMaximo);
        this.candidatos = List.copyOf(candidatos);
    }

    public Scrim getScrim() { return scrim; }
    public int getCupoMaximo() { return cupoMaximo; }
    public List<CandidatoMatchmaking> getCandidatos() { return Collections.unmodifiableList(candidatos); }
}
