package com.escrims.domain.model.busqueda;

import com.escrims.domain.model.juego.Juego;
import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.valueobjects.DisponibilidadHoraria;
import com.escrims.domain.valueobjects.Rango;
import com.escrims.domain.valueobjects.Region;
import com.escrims.domain.valueobjects.RolJuego;

import java.util.UUID;

public class BusquedaFavorita {

    private final UUID id;
    private final UUID usuarioId;
    private final Juego juego;
    private final Rango rangoMinimo;
    private final Rango rangoMaximo;
    private final Region region;
    private final DisponibilidadHoraria horarioPreferido;
    private final RolJuego rolBuscado;
    private boolean alertaActiva;

    public BusquedaFavorita(UUID usuarioId, Juego juego, Rango rangoMinimo, Rango rangoMaximo,
                            Region region, DisponibilidadHoraria horarioPreferido, RolJuego rolBuscado) {
        this.id = UUID.randomUUID();
        this.usuarioId = usuarioId;
        this.juego = juego;
        this.rangoMinimo = rangoMinimo;
        this.rangoMaximo = rangoMaximo;
        this.region = region;
        this.horarioPreferido = horarioPreferido;
        this.rolBuscado = rolBuscado;
        this.alertaActiva = false;
    }

    public boolean coincideCon(Scrim scrim) {
        if (!juego.getNombre().equalsIgnoreCase(scrim.getJuego().getNombre())) return false;
        if (region != null && !region.equals(scrim.getRegion())) return false;
        if (rangoMinimo != null && scrim.getRangosPermitidos().getMin().getNumerico() < rangoMinimo.getNumerico()) {
            return false;
        }
        if (rangoMaximo != null && scrim.getRangosPermitidos().getMax().getNumerico() > rangoMaximo.getNumerico()) {
            return false;
        }
        return true;
    }

    public void activarAlerta() { this.alertaActiva = true; }
    public void desactivarAlerta() { this.alertaActiva = false; }

    public UUID getId() { return id; }
    public UUID getUsuarioId() { return usuarioId; }
    public Juego getJuego() { return juego; }
    public Rango getRangoMinimo() { return rangoMinimo; }
    public Rango getRangoMaximo() { return rangoMaximo; }
    public Region getRegion() { return region; }
    public DisponibilidadHoraria getHorarioPreferido() { return horarioPreferido; }
    public RolJuego getRolBuscado() { return rolBuscado; }
    public boolean isAlertaActiva() { return alertaActiva; }
}
