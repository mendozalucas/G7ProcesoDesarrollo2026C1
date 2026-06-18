package com.escrims.domain.model.scrim;

import com.escrims.domain.model.usuario.Jugador;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Equipo {

    private Long id;
    private String nombre;
    private List<Jugador> jugadores;

    public Equipo(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.jugadores = new ArrayList<>();
    }

    public void agregarJugador(Jugador jugador) {
        if (!jugadores.contains(jugador)) {
            jugadores.add(jugador);
        }
    }

    public void quitarJugador(Jugador jugador) {
        jugadores.remove(jugador);
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public List<Jugador> getJugadores() { return Collections.unmodifiableList(jugadores); }
    public int getCuposOcupados() { return jugadores.size(); }
}
