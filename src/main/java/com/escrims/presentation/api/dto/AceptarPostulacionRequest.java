package com.escrims.presentation.api.dto;

import java.util.UUID;

public class AceptarPostulacionRequest {

    private UUID postulacionId;
    private String lado;

    public UUID getPostulacionId() { return postulacionId; }
    public void setPostulacionId(UUID postulacionId) { this.postulacionId = postulacionId; }
    public String getLado() { return lado; }
    public void setLado(String lado) { this.lado = lado; }
}
