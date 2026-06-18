package com.escrims.domain.model.scrim;

import com.escrims.domain.events.DomainEvent;
import com.escrims.domain.model.juego.Juego;
import com.escrims.domain.model.lobby.Lobby;
import com.escrims.domain.model.usuario.Jugador;
import com.escrims.domain.state.ScrimState;
import com.escrims.domain.state.BuscandoJugadoresState;
import com.escrims.domain.valueobjects.FormatoScrim;
import com.escrims.domain.valueobjects.Rango;
import com.escrims.domain.valueobjects.Region;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Scrim {

    private final UUID id;
    private Juego juego;
    private FormatoScrim formato;
    private String modalidad;
    private Jugador organizador;
    private Region region;
    private Rango rangoMin;
    private Rango rangoMax;
    private int latenciaMax;
    private LocalDateTime fechaHora;
    private Lobby lobby;
    private ScrimState currentState;
    private String motivoCancelacion;
    private final List<DomainEvent> domainEvents;

    public Scrim(UUID id, Juego juego, FormatoScrim formato, String modalidad, Jugador organizador,
                 Region region, Rango rangoMin, Rango rangoMax, int latenciaMax, LocalDateTime fechaHora) {
        this.id = id;
        this.juego = juego;
        this.formato = formato != null ? formato : new FormatoScrim(5);
        this.modalidad = modalidad != null ? modalidad : "CASUAL";
        this.organizador = organizador;
        this.region = region;
        this.rangoMin = rangoMin;
        this.rangoMax = rangoMax;
        this.latenciaMax = latenciaMax;
        this.fechaHora = fechaHora;
        this.lobby = new Lobby(id, this.formato);
        this.currentState = new BuscandoJugadoresState();
        this.domainEvents = new ArrayList<>();
    }

    public void registrarParticipantesLobby(List<UUID> ids) {
        // Los IDs se sincronizan vía GestorLobby en matchmaking; este método mantiene compatibilidad.
    }

    public List<UUID> getParticipantesLobby() {
        return lobby.getParticipantes();
    }

    public void cancelar(String motivo) {
        this.motivoCancelacion = motivo;
        currentState.cancelar(this);
    }

    public void avanzarEstado() {
        currentState.avanzar(this);
    }

    public void cambiarEstado(ScrimState nuevoEstado) {
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

    public String getEstadoNombre() { return currentState.getNombreEstado(); }
    public String getMotivoCancelacion() { return motivoCancelacion != null ? motivoCancelacion : ""; }

    public UUID getId() { return id; }
    public Juego getJuego() { return juego; }
    public FormatoScrim getFormato() { return formato; }
    public String getModalidad() { return modalidad; }
    public Jugador getOrganizador() { return organizador; }
    public Region getRegion() { return region; }
    public Rango getRangoMin() { return rangoMin; }
    public Rango getRangoMax() { return rangoMax; }
    public int getLatenciaMax() { return latenciaMax; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public Lobby getLobby() { return lobby; }
    public void setLobby(Lobby lobby) { this.lobby = lobby; }
    public ScrimState getCurrentState() { return currentState; }
}
