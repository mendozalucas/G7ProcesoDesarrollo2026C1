package com.escrims.domain.model.postulacion;

import com.escrims.domain.events.DomainEvent;
import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.model.usuario.Usuario;
import com.escrims.domain.state.postulacion.PendientePostulacionState;
import com.escrims.domain.state.postulacion.PostulacionState;
import com.escrims.domain.state.postulacion.PostulacionStateFactory;
import com.escrims.domain.valueobjects.RolJuego;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Postulacion {

    private final UUID id;
    private final UUID usuarioId;
    private final UUID scrimId;
    private final Usuario usuario;
    private final Scrim scrim;
    private final RolJuego rolDeseado;
    private PostulacionState currentState;
    private final LocalDateTime fechaPostulacion;
    private final List<DomainEvent> domainEvents;

    public Postulacion(Usuario usuario, Scrim scrim, RolJuego rolDeseado) {
        this.id = UUID.randomUUID();
        this.usuario = usuario;
        this.scrim = scrim;
        this.usuarioId = usuario.getId();
        this.scrimId = scrim.getId();
        this.rolDeseado = rolDeseado;
        this.currentState = new PendientePostulacionState();
        this.fechaPostulacion = LocalDateTime.now();
        this.domainEvents = new ArrayList<>();
    }

    public Postulacion(UUID usuarioId, UUID scrimId, RolJuego rolDeseado) {
        this.id = UUID.randomUUID();
        this.usuarioId = usuarioId;
        this.scrimId = scrimId;
        this.usuario = null;
        this.scrim = null;
        this.rolDeseado = rolDeseado;
        this.currentState = new PendientePostulacionState();
        this.fechaPostulacion = LocalDateTime.now();
        this.domainEvents = new ArrayList<>();
    }

    public void aceptar() {
        currentState.aceptar(this);
    }

    public void rechazar() {
        currentState.rechazar(this);
    }

    public String getEstadoNombre() {
        return currentState.getNombre();
    }

    public void cambiarEstado(PostulacionState nuevoEstado) {
        this.currentState = nuevoEstado;
    }

    public void agregarEvento(DomainEvent event) {
        this.domainEvents.add(event);
    }

    public List<DomainEvent> recolectarEventos() {
        List<DomainEvent> eventos = new ArrayList<>(domainEvents);
        domainEvents.clear();
        return eventos;
    }

    public UUID getId() { return id; }
    public Usuario getUsuario() { return usuario; }
    public UUID getUsuarioId() { return usuarioId; }
    public Scrim getScrim() { return scrim; }
    public UUID getScrimId() { return scrimId; }
    public RolJuego getRolDeseado() { return rolDeseado; }
    public LocalDateTime getFechaPostulacion() { return fechaPostulacion; }

    public static Postulacion reconstituir(UUID id, UUID usuarioId, UUID scrimId,
                                           RolJuego rolDeseado, String estadoNombre,
                                           LocalDateTime fechaPostulacion) {
        Postulacion p = new Postulacion(id, usuarioId, scrimId, rolDeseado,
                PostulacionStateFactory.para(estadoNombre), fechaPostulacion);
        return p;
    }

    private Postulacion(UUID id, UUID usuarioId, UUID scrimId, RolJuego rolDeseado,
                          PostulacionState estado, LocalDateTime fechaPostulacion) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.scrimId = scrimId;
        this.usuario = null;
        this.scrim = null;
        this.rolDeseado = rolDeseado;
        this.currentState = estado;
        this.fechaPostulacion = fechaPostulacion;
        this.domainEvents = new ArrayList<>();
    }
}
