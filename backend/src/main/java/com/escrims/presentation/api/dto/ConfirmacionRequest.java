package com.escrims.presentation.api.dto;

import java.util.UUID;

public class ConfirmacionRequest {

    private UUID usuarioId;

    public UUID getUsuarioId() { return usuarioId; }
    public void setUsuarioId(UUID usuarioId) { this.usuarioId = usuarioId; }
}
