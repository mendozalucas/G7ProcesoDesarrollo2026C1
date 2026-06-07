package com.escrims.infrastructure.persistence.jpa.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "scrims")
public class ScrimEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String juego;

    private int jugadoresPorLado;
    private String regionServidor;
    private String regionZona;
    private String rangoMinJuego;
    private String rangoMinTier;
    private int rangoMinNumerico;
    private String rangoMaxJuego;
    private String rangoMaxTier;
    private int rangoMaxNumerico;
    private int latenciaMaxMs;
    private LocalDateTime fechaHora;
    private long duracionMinutos;
    private String modalidad;
    private String estado;
    private String motivoCancelacion;
    private UUID organizadorId;
    private UUID capitanEquipoA;
    private UUID capitanEquipoB;

    @ElementCollection
    @CollectionTable(name = "scrim_reglas_roles", joinColumns = @JoinColumn(name = "scrim_id"))
    private List<ReglaRolEmbeddable> reglasRoles = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "scrim_participantes", joinColumns = @JoinColumn(name = "scrim_id"))
    private List<ParticipanteEmbeddable> participantes = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "scrim_confirmaciones", joinColumns = @JoinColumn(name = "scrim_id"))
    private List<ConfirmacionEmbeddable> confirmaciones = new ArrayList<>();

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getJuego() { return juego; }
    public void setJuego(String juego) { this.juego = juego; }
    public int getJugadoresPorLado() { return jugadoresPorLado; }
    public void setJugadoresPorLado(int jugadoresPorLado) { this.jugadoresPorLado = jugadoresPorLado; }
    public String getRegionServidor() { return regionServidor; }
    public void setRegionServidor(String regionServidor) { this.regionServidor = regionServidor; }
    public String getRegionZona() { return regionZona; }
    public void setRegionZona(String regionZona) { this.regionZona = regionZona; }
    public String getRangoMinJuego() { return rangoMinJuego; }
    public void setRangoMinJuego(String rangoMinJuego) { this.rangoMinJuego = rangoMinJuego; }
    public String getRangoMinTier() { return rangoMinTier; }
    public void setRangoMinTier(String rangoMinTier) { this.rangoMinTier = rangoMinTier; }
    public int getRangoMinNumerico() { return rangoMinNumerico; }
    public void setRangoMinNumerico(int rangoMinNumerico) { this.rangoMinNumerico = rangoMinNumerico; }
    public String getRangoMaxJuego() { return rangoMaxJuego; }
    public void setRangoMaxJuego(String rangoMaxJuego) { this.rangoMaxJuego = rangoMaxJuego; }
    public String getRangoMaxTier() { return rangoMaxTier; }
    public void setRangoMaxTier(String rangoMaxTier) { this.rangoMaxTier = rangoMaxTier; }
    public int getRangoMaxNumerico() { return rangoMaxNumerico; }
    public void setRangoMaxNumerico(int rangoMaxNumerico) { this.rangoMaxNumerico = rangoMaxNumerico; }
    public int getLatenciaMaxMs() { return latenciaMaxMs; }
    public void setLatenciaMaxMs(int latenciaMaxMs) { this.latenciaMaxMs = latenciaMaxMs; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
    public long getDuracionMinutos() { return duracionMinutos; }
    public void setDuracionMinutos(long duracionMinutos) { this.duracionMinutos = duracionMinutos; }
    public String getModalidad() { return modalidad; }
    public void setModalidad(String modalidad) { this.modalidad = modalidad; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getMotivoCancelacion() { return motivoCancelacion; }
    public void setMotivoCancelacion(String motivoCancelacion) { this.motivoCancelacion = motivoCancelacion; }
    public UUID getOrganizadorId() { return organizadorId; }
    public void setOrganizadorId(UUID organizadorId) { this.organizadorId = organizadorId; }
    public UUID getCapitanEquipoA() { return capitanEquipoA; }
    public void setCapitanEquipoA(UUID capitanEquipoA) { this.capitanEquipoA = capitanEquipoA; }
    public UUID getCapitanEquipoB() { return capitanEquipoB; }
    public void setCapitanEquipoB(UUID capitanEquipoB) { this.capitanEquipoB = capitanEquipoB; }
    public List<ReglaRolEmbeddable> getReglasRoles() { return reglasRoles; }
    public void setReglasRoles(List<ReglaRolEmbeddable> reglasRoles) { this.reglasRoles = reglasRoles; }
    public List<ParticipanteEmbeddable> getParticipantes() { return participantes; }
    public void setParticipantes(List<ParticipanteEmbeddable> participantes) { this.participantes = participantes; }
    public List<ConfirmacionEmbeddable> getConfirmaciones() { return confirmaciones; }
    public void setConfirmaciones(List<ConfirmacionEmbeddable> confirmaciones) { this.confirmaciones = confirmaciones; }
}
