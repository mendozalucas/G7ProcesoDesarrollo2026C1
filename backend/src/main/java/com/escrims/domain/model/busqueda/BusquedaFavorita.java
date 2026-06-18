package com.escrims.domain.model.busqueda;

import com.escrims.domain.model.juego.Juego;
import com.escrims.domain.model.rol.Rol;
import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.valueobjects.DisponibilidadHoraria;
import com.escrims.domain.valueobjects.Rango;
import com.escrims.domain.valueobjects.Region;

public class BusquedaFavorita {

    private final Juego juego;
    private final Rango rangoMinimo;
    private final Rango rangoMaximo;
    private final Region region;
    private final DisponibilidadHoraria horarioPreferido;
    private final Rol rolBuscado;

    public BusquedaFavorita(Juego juego, Rango rangoMinimo, Rango rangoMaximo,
                            Region region, DisponibilidadHoraria horarioPreferido, Rol rolBuscado) {
        this.juego = juego;
        this.rangoMinimo = rangoMinimo;
        this.rangoMaximo = rangoMaximo;
        this.region = region;
        this.horarioPreferido = horarioPreferido;
        this.rolBuscado = rolBuscado;
    }

    public boolean coincideCon(Scrim scrim) {
        return juego.getNombre().equalsIgnoreCase(scrim.getJuego().getNombre())
                && region.equals(scrim.getRegion());
    }

    public Juego getJuego() { return juego; }
    public Rango getRangoMinimo() { return rangoMinimo; }
    public Rango getRangoMaximo() { return rangoMaximo; }
    public Region getRegion() { return region; }
    public DisponibilidadHoraria getHorarioPreferido() { return horarioPreferido; }
    public Rol getRolBuscado() { return rolBuscado; }
}
