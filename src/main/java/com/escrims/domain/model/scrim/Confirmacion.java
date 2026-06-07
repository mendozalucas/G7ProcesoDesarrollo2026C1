package com.escrims.domain.model.scrim;

import java.time.LocalDateTime;
import java.util.UUID;

public class Confirmacion {

    private final UUID id;
    private final UUID usuarioId;
    private boolean confirmado;
    private LocalDateTime fechaConfirmacion;

    public Confirmacion(UUID usuarioId) {
        this.id = UUID.randomUUID();
        this.usuarioId = usuarioId;
        this.confirmado = false;
    }

    public void confirmar() {
        this.confirmado = true;
        this.fechaConfirmacion = LocalDateTime.now();
    }

    public boolean esPendiente() { return !confirmado; }

    public UUID getId() { return id; }
    public UUID getUsuarioId() { return usuarioId; }
    public boolean isConfirmado() { return confirmado; }
    public LocalDateTime getFechaConfirmacion() { return fechaConfirmacion; }
}
