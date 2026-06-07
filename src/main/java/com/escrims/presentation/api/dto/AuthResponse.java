package com.escrims.presentation.api.dto;

import java.util.UUID;

public class AuthResponse {

    private UUID usuarioId;

    public AuthResponse(UUID usuarioId) {
        this.usuarioId = usuarioId;
    }

    public UUID getUsuarioId() { return usuarioId; }
}
