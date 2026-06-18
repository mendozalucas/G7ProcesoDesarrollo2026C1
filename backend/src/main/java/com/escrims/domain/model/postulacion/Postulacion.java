package com.escrims.domain.model.postulacion;

import com.escrims.domain.model.rol.Rol;
import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.model.usuario.Jugador;
import com.escrims.domain.model.usuario.Usuario;
import com.escrims.domain.state.postulacion.AceptadaPostulacionState;
import com.escrims.domain.state.postulacion.PendientePostulacionState;
import com.escrims.domain.state.postulacion.PostulacionState;
import com.escrims.domain.state.postulacion.PostulacionStateFactory;
import com.escrims.domain.state.postulacion.RechazadaPostulacionState;

public class Postulacion {

    private Long id;
    private Jugador jugador;
    private Scrim scrim;
    private Rol rolDeseado;
    private PostulacionState currentState;

    public Postulacion(Long id, Jugador jugador, Scrim scrim, Rol rolDeseado, EstadoPostulacion estado) {
        this.id = id;
        this.jugador = jugador;
        this.scrim = scrim;
        this.rolDeseado = rolDeseado;
        this.currentState = estado != null
                ? PostulacionStateFactory.para(estado.name())
                : new PendientePostulacionState();
    }

    public void aceptar() {
        currentState.aceptar(this);
    }

    public void rechazar() {
        currentState.rechazar(this);
    }

    public void transicionarA(PostulacionState nuevoEstado) {
        this.currentState = nuevoEstado;
    }

    public boolean estaPendiente() {
        return currentState instanceof PendientePostulacionState;
    }

    public boolean estaAceptada() {
        return currentState instanceof AceptadaPostulacionState;
    }

    public Long getId() { return id; }
    public Jugador getJugador() { return jugador; }
    public Usuario getUsuario() { return jugador; }
    public Scrim getScrim() { return scrim; }
    public Rol getRolDeseado() { return rolDeseado; }
    public EstadoPostulacion getEstado() {
        return EstadoPostulacion.valueOf(currentState.getNombre());
    }
    public PostulacionState getCurrentState() { return currentState; }
}
