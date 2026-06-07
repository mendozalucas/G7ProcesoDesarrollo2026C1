package com.escrims.infrastructure.persistence.jpa.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "postulaciones")
public class PostulacionEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID usuarioId;

    @Column(nullable = false)
    private UUID scrimId;

    private String rolJuego;
    private String rolNombre;
    private String estado;
    private LocalDateTime fechaPostulacion;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getUsuarioId() { return usuarioId; }
    public void setUsuarioId(UUID usuarioId) { this.usuarioId = usuarioId; }
    public UUID getScrimId() { return scrimId; }
    public void setScrimId(UUID scrimId) { this.scrimId = scrimId; }
    public String getRolJuego() { return rolJuego; }
    public void setRolJuego(String rolJuego) { this.rolJuego = rolJuego; }
    public String getRolNombre() { return rolNombre; }
    public void setRolNombre(String rolNombre) { this.rolNombre = rolNombre; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public LocalDateTime getFechaPostulacion() { return fechaPostulacion; }
    public void setFechaPostulacion(LocalDateTime fechaPostulacion) { this.fechaPostulacion = fechaPostulacion; }
}
