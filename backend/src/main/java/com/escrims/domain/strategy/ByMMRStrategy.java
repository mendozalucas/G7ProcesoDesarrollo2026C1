package com.escrims.domain.strategy;

import com.escrims.domain.model.usuario.Usuario;

import java.util.List;

public class ByMMRStrategy implements IMatchmakingStrategy {

    private int diferenciaMMRMaxima;

    public ByMMRStrategy(int diferenciaMMRMaxima) {
        this.diferenciaMMRMaxima = diferenciaMMRMaxima;
    }

    @Override
    public List<Usuario> seleccionar(List<Usuario> candidatos) {
        return candidatos;
    }

    public int getDiferenciaMMRMaxima() { return diferenciaMMRMaxima; }
}
