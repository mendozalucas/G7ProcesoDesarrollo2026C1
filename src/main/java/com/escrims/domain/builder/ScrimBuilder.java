package com.escrims.domain.builder;

import com.escrims.domain.model.juego.Juego;
import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.valueobjects.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ScrimBuilder {

    private Juego juego;
    private FormatoScrim formato;
    private Region region;
    private Rango rangoMin;
    private Rango rangoMax;
    private Latencia latenciaMax;
    private LocalDateTime fechaHora;
    private Duration duracionEstimada;
    private Modalidad modalidad;
    private UUID organizadorId;
    private Map<RolJuego, Integer> reglasRoles = new HashMap<>();

    public ScrimBuilder conJuego(Juego juego)               { this.juego = juego; return this; }
    public ScrimBuilder conFormato(FormatoScrim f)          { this.formato = f; return this; }
    public ScrimBuilder enRegion(Region r)                  { this.region = r; return this; }
    public ScrimBuilder conRango(Rango min, Rango max)      { this.rangoMin = min; this.rangoMax = max; return this; }
    public ScrimBuilder conRangos(Rango min, Rango max)     { return conRango(min, max); }
    public ScrimBuilder conRegion(Region r)                 { return enRegion(r); }
    public ScrimBuilder conLatenciaMax(Latencia l)          { this.latenciaMax = l; return this; }
    public ScrimBuilder conLatenciaMax(int ms)              { return conLatenciaMax(new Latencia(ms)); }
    public ScrimBuilder enFechaHora(LocalDateTime dt)       { this.fechaHora = dt; return this; }
    public ScrimBuilder conFechaHora(LocalDateTime dt)      { return enFechaHora(dt); }
    public ScrimBuilder conDuracion(Duration d)             { this.duracionEstimada = d; return this; }
    public ScrimBuilder enModalidad(Modalidad m)            { this.modalidad = m; return this; }
    public ScrimBuilder creadoPor(UUID id)                  { this.organizadorId = id; return this; }
    public ScrimBuilder conReglasRoles(Map<RolJuego,Integer> r){ this.reglasRoles = new HashMap<>(r); return this; }

    public Scrim build() {
        validarCamposObligatorios();
        RangosPermitidos rangos = new RangosPermitidos(rangoMin, rangoMax);
        return new Scrim(
                UUID.randomUUID(), juego, formato, region, rangos,
                latenciaMax != null ? latenciaMax : new Latencia(999),
                fechaHora,
                duracionEstimada != null ? duracionEstimada : Duration.ofHours(1),
                modalidad != null ? modalidad : new ModalidadCasual(),
                reglasRoles, organizadorId
        );
    }

    private void validarCamposObligatorios() {
        if (juego == null)         throw new IllegalStateException("Juego obligatorio");
        if (formato == null)       throw new IllegalStateException("Formato obligatorio");
        if (region == null)        throw new IllegalStateException("Region obligatoria");
        if (rangoMin == null || rangoMax == null) throw new IllegalStateException("Rangos obligatorios");
        if (fechaHora == null)     throw new IllegalStateException("Fecha/hora obligatoria");
        if (fechaHora.isBefore(LocalDateTime.now())) throw new IllegalStateException("La fecha debe ser futura");
        if (organizadorId == null) throw new IllegalStateException("Organizador obligatorio");
    }
}
