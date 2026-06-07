package com.escrims.domain.strategy;

import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.model.usuario.Usuario;
import com.escrims.domain.valueobjects.Rango;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ByMMRStrategy implements IMatchmakingStrategy {

    private final int diferenciaMMRMaxima;
    private final Scrim scrim;

    public ByMMRStrategy(int diferenciaMMRMaxima, Scrim scrim) {
        this.diferenciaMMRMaxima = diferenciaMMRMaxima;
        this.scrim = scrim;
    }

    @Override
    public List<Usuario> seleccionar(List<Usuario> candidatos) {
        return candidatos.stream()
                .filter(u -> !u.estaBloqueado())
                .filter(u -> scrim.getRangosPermitidos().contiene(u.getRangoPara(scrim.getNombreJuego())))
                .sorted(Comparator.comparingDouble(this::calcularPuntuacion))
                .collect(Collectors.toList());
    }

    private double calcularPuntuacion(Usuario usuario) {
        Rango rango = usuario.getRangoPara(scrim.getNombreJuego());
        if (rango == null) return Double.MAX_VALUE;
        double promedio = promedioMMR();
        double diff = Math.abs(rango.getNumerico() - promedio);
        return diff <= diferenciaMMRMaxima ? diff : Double.MAX_VALUE;
    }

    private double promedioMMR() {
        int min = scrim.getRangosPermitidos().getMin().getNumerico();
        int max = scrim.getRangosPermitidos().getMax().getNumerico();
        return (min + max) / 2.0;
    }
}
