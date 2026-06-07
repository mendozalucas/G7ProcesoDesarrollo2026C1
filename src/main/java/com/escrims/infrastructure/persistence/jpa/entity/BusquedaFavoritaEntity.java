package com.escrims.infrastructure.persistence.jpa.entity;

import jakarta.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "busquedas_favoritas")
public class BusquedaFavoritaEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID usuarioId;

    private String juego;
    private String rangoMinJuego;
    private String rangoMinTier;
    private Integer rangoMinNumerico;
    private String rangoMaxJuego;
    private String rangoMaxTier;
    private Integer rangoMaxNumerico;
    private String regionServidor;
    private String regionZona;
    @Enumerated(EnumType.STRING)
    private DayOfWeek horarioDia;
    private LocalTime horarioInicio;
    private LocalTime horarioFin;
    private String rolJuego;
    private String rolNombre;
    private boolean alertaActiva;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getUsuarioId() { return usuarioId; }
    public void setUsuarioId(UUID usuarioId) { this.usuarioId = usuarioId; }
    public String getJuego() { return juego; }
    public void setJuego(String juego) { this.juego = juego; }
    public String getRangoMinJuego() { return rangoMinJuego; }
    public void setRangoMinJuego(String rangoMinJuego) { this.rangoMinJuego = rangoMinJuego; }
    public String getRangoMinTier() { return rangoMinTier; }
    public void setRangoMinTier(String rangoMinTier) { this.rangoMinTier = rangoMinTier; }
    public Integer getRangoMinNumerico() { return rangoMinNumerico; }
    public void setRangoMinNumerico(Integer rangoMinNumerico) { this.rangoMinNumerico = rangoMinNumerico; }
    public String getRangoMaxJuego() { return rangoMaxJuego; }
    public void setRangoMaxJuego(String rangoMaxJuego) { this.rangoMaxJuego = rangoMaxJuego; }
    public String getRangoMaxTier() { return rangoMaxTier; }
    public void setRangoMaxTier(String rangoMaxTier) { this.rangoMaxTier = rangoMaxTier; }
    public Integer getRangoMaxNumerico() { return rangoMaxNumerico; }
    public void setRangoMaxNumerico(Integer rangoMaxNumerico) { this.rangoMaxNumerico = rangoMaxNumerico; }
    public String getRegionServidor() { return regionServidor; }
    public void setRegionServidor(String regionServidor) { this.regionServidor = regionServidor; }
    public String getRegionZona() { return regionZona; }
    public void setRegionZona(String regionZona) { this.regionZona = regionZona; }
    public DayOfWeek getHorarioDia() { return horarioDia; }
    public void setHorarioDia(DayOfWeek horarioDia) { this.horarioDia = horarioDia; }
    public LocalTime getHorarioInicio() { return horarioInicio; }
    public void setHorarioInicio(LocalTime horarioInicio) { this.horarioInicio = horarioInicio; }
    public LocalTime getHorarioFin() { return horarioFin; }
    public void setHorarioFin(LocalTime horarioFin) { this.horarioFin = horarioFin; }
    public String getRolJuego() { return rolJuego; }
    public void setRolJuego(String rolJuego) { this.rolJuego = rolJuego; }
    public String getRolNombre() { return rolNombre; }
    public void setRolNombre(String rolNombre) { this.rolNombre = rolNombre; }
    public boolean isAlertaActiva() { return alertaActiva; }
    public void setAlertaActiva(boolean alertaActiva) { this.alertaActiva = alertaActiva; }
}
