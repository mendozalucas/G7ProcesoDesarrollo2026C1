package com.escrims.domain.builder;

import com.escrims.domain.model.juego.Juego;
import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.model.usuario.Jugador;
import com.escrims.domain.valueobjects.FormatoScrim;
import com.escrims.domain.valueobjects.Rango;
import com.escrims.domain.valueobjects.Region;

import java.time.LocalDateTime;
import java.util.UUID;

public class ScrimBuilder {

    private Juego juego;
    private FormatoScrim formato;
    private String modalidad;
    private Jugador organizador;
    private Region region;
    private Rango rangoMin;
    private Rango rangoMax;
    private int latenciaMax;
    private LocalDateTime fechaHora;

    public ScrimBuilder conJuego(Juego juego)              { this.juego = juego; return this; }
    public ScrimBuilder conFormato(FormatoScrim formato)   { this.formato = formato; return this; }
    public ScrimBuilder conModalidad(String modalidad)     { this.modalidad = modalidad; return this; }
    public ScrimBuilder conRegion(Region r)                { this.region = r; return this; }
    public ScrimBuilder conRango(Rango min, Rango max)     { this.rangoMin = min; this.rangoMax = max; return this; }
    public ScrimBuilder conLatenciaMax(int ms)             { this.latenciaMax = ms; return this; }
    public ScrimBuilder conFechaHora(LocalDateTime dt)     { this.fechaHora = dt; return this; }
    public ScrimBuilder creadoPor(Jugador organizador)     { this.organizador = organizador; return this; }

    public Scrim build() {
        if (juego == null)                          throw new IllegalStateException("Juego obligatorio");
        if (formato == null)                        throw new IllegalStateException("Formato obligatorio");
        if (region == null)                         throw new IllegalStateException("Region obligatoria");
        if (rangoMin == null || rangoMax == null)   throw new IllegalStateException("Rangos obligatorios");
        if (fechaHora == null)                      throw new IllegalStateException("Fecha/hora obligatoria");
        if (organizador == null)                    throw new IllegalStateException("Organizador obligatorio");

        return new Scrim(UUID.randomUUID(), juego, formato, modalidad, organizador, region,
                rangoMin, rangoMax, latenciaMax, fechaHora);
    }
}
