package com.escrims.domain.valueobjects;

import java.time.LocalDateTime;
import java.util.Objects;

public final class CriteriosBusqueda {

    private final String juego;
    private final FormatoScrim formato;
    private final RangosPermitidos rangosPermitidos;
    private final Region region;
    private final LocalDateTime desde;
    private final LocalDateTime hasta;
    private final Latencia latenciaMax;

    public CriteriosBusqueda(String juego,
                              FormatoScrim formato,
                              RangosPermitidos rangosPermitidos,
                              Region region,
                              LocalDateTime desde,
                              LocalDateTime hasta,
                              Latencia latenciaMax) {
        this.juego = juego;
        this.formato = formato;
        this.rangosPermitidos = rangosPermitidos;
        this.region = region;
        this.desde = desde;
        this.hasta = hasta;
        this.latenciaMax = latenciaMax;
    }

    public boolean coincideCon(com.escrims.domain.model.scrim.Scrim scrim) {
        if (this.juego != null && !this.juego.equalsIgnoreCase(scrim.getJuego().getNombre())) return false;
        if (this.region != null && !this.region.equals(scrim.getRegion())) return false;
        if (this.desde != null && scrim.getFechaHora().isBefore(this.desde)) return false;
        if (this.hasta != null && scrim.getFechaHora().isAfter(this.hasta)) return false;
        if (this.latenciaMax != null && scrim.getLatenciaMax() > this.latenciaMax.getPingMs()) return false;
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CriteriosBusqueda)) return false;
        CriteriosBusqueda that = (CriteriosBusqueda) o;
        return Objects.equals(juego, that.juego)
                && Objects.equals(formato, that.formato)
                && Objects.equals(rangosPermitidos, that.rangosPermitidos)
                && Objects.equals(region, that.region);
    }

    @Override
    public int hashCode() {
        return Objects.hash(juego, formato, rangosPermitidos, region);
    }

    public String getJuego() { return juego; }
    public FormatoScrim getFormato() { return formato; }
    public RangosPermitidos getRangosPermitidos() { return rangosPermitidos; }
    public Region getRegion() { return region; }
    public LocalDateTime getDesde() { return desde; }
    public LocalDateTime getHasta() { return hasta; }
    public Latencia getLatenciaMax() { return latenciaMax; }
}
