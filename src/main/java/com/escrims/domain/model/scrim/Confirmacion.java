package com.escrims.domain.model.scrim;

import java.time.LocalDateTime;
import java.util.UUID;

public class Confirmacion {

    private final UUID id;
    private final UUID usuarioId;
    private boolean confirmado;
    private LocalDateTime fechaConfirmacion;

    public Confirmacion(UUID usuarioId) {
        this(UUID.randomUUID(), usuarioId, false, null);
    }

    private Confirmacion(UUID id, UUID usuarioId, boolean confirmado, LocalDateTime fechaConfirmacion) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.confirmado = confirmado;
        this.fechaConfirmacion = fechaConfirmacion;
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

    public static Confirmacion reconstituir(UUID id, UUID usuarioId, boolean confirmado,
                                          LocalDateTime fechaConfirmacion) {
        return new Confirmacion(id, usuarioId, confirmado,
                confirmado ? fechaConfirmacion : null);
    }
}
