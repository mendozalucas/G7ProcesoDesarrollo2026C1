package com.escrims.domain.strategy;

import com.escrims.domain.model.usuario.Usuario;

import java.util.List;

public class ByHistoryStrategy implements IMatchmakingStrategy {

    private double pesoMMR;
    private double pesoLatencia;
    private double pesoHistorial;

    public ByHistoryStrategy(double pesoMMR, double pesoLatencia, double pesoHistorial) {
        this.pesoMMR = pesoMMR;
        this.pesoLatencia = pesoLatencia;
        this.pesoHistorial = pesoHistorial;
    }

    @Override
    public List<Usuario> seleccionar(List<Usuario> candidatos) {
        return candidatos;
    }

    public double getPesoMMR() { return pesoMMR; }
    public double getPesoLatencia() { return pesoLatencia; }
    public double getPesoHistorial() { return pesoHistorial; }
}
