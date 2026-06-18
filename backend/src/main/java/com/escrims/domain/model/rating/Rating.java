package com.escrims.domain.model.rating;

import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.model.usuario.Jugador;

import java.util.UUID;

public class Rating {

    private Long id;
    private final Jugador calificador;
    private final Jugador calificado;
    private final Scrim scrim;
    private final int puntuacion;
    private final String comentario;

    public Rating(Long id, Jugador calificador, Jugador calificado, Scrim scrim,
                  int puntuacion, String comentario) {
        this.id = id;
        this.calificador = calificador;
        this.calificado = calificado;
        this.scrim = scrim;
        this.puntuacion = puntuacion;
        this.comentario = comentario != null ? comentario : "";
    }

    public void asignarId(Long id) {
        this.id = id;
    }

    public Long getId() { return id; }
    public Jugador getCalificador() { return calificador; }
    public Jugador getCalificado() { return calificado; }
    public Scrim getScrim() { return scrim; }
    public int getPuntuacion() { return puntuacion; }
    public String getComentario() { return comentario; }
    public UUID getScrimId() { return scrim.getId(); }
}
