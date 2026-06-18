package com.escrims.domain.strategy;

import com.escrims.domain.model.usuario.Usuario;

import java.util.List;

public class ByLatencyStrategy implements IMatchmakingStrategy {

    private int latenciaMaxima;

    public ByLatencyStrategy(int latenciaMaxima) {
        this.latenciaMaxima = latenciaMaxima;
    }

    @Override
    public List<Usuario> seleccionar(List<Usuario> candidatos) {
        return candidatos;
    }

    public int getLatenciaMaxima() { return latenciaMaxima; }
}
