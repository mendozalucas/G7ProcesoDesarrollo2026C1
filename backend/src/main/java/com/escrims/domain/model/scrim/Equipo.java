package com.escrims.domain.model.scrim;

import com.escrims.domain.model.usuario.Usuario;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Equipo {

    private Long id;
    private String nombre;
    private List<Usuario> jugadores;

    public Equipo(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.jugadores = new ArrayList<>();
    }

    public void agregarJugador(Usuario usuario) {
        jugadores.add(usuario);
    }

    public void quitarJugador(Usuario usuario) {
        jugadores.remove(usuario);
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public List<Usuario> getJugadores() { return Collections.unmodifiableList(jugadores); }
    public int getCuposOcupados() { return jugadores.size(); }
}
