package com.escrims.presentation.api.dto;

import java.util.UUID;

public class CalificacionRequest {

    private UUID scrimId;
    private UUID calificadorId;
    private UUID calificadoId;
    private int puntuacion;
    private String comentario;

    public UUID getScrimId() { return scrimId; }
    public void setScrimId(UUID scrimId) { this.scrimId = scrimId; }
    public UUID getCalificadorId() { return calificadorId; }
    public void setCalificadorId(UUID calificadorId) { this.calificadorId = calificadorId; }
    public UUID getCalificadoId() { return calificadoId; }
    public void setCalificadoId(UUID calificadoId) { this.calificadoId = calificadoId; }
    public int getPuntuacion() { return puntuacion; }
    public void setPuntuacion(int puntuacion) { this.puntuacion = puntuacion; }
    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }
}
