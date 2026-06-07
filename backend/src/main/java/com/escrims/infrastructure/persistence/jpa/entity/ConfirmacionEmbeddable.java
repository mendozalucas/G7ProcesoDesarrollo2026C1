package com.escrims.infrastructure.persistence.jpa.entity;

import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;
import java.util.UUID;

@Embeddable
public class ConfirmacionEmbeddable {

    private UUID id;
    private UUID usuarioId;
    private boolean confirmado;
    private LocalDateTime fechaConfirmacion;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getUsuarioId() { return usuarioId; }
    public void setUsuarioId(UUID usuarioId) { this.usuarioId = usuarioId; }
    public boolean isConfirmado() { return confirmado; }
    public void setConfirmado(boolean confirmado) { this.confirmado = confirmado; }
    public LocalDateTime getFechaConfirmacion() { return fechaConfirmacion; }
    public void setFechaConfirmacion(LocalDateTime fechaConfirmacion) { this.fechaConfirmacion = fechaConfirmacion; }
}
