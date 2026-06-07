package com.escrims.domain.model.scrim;

import com.escrims.domain.events.DomainEvent;
import com.escrims.domain.events.ScrimCreadoEvent;
import com.escrims.domain.model.juego.Juego;
import com.escrims.domain.state.BuscandoJugadoresState;
import com.escrims.domain.state.LobbyArmadoState;
import com.escrims.domain.state.ScrimState;
import com.escrims.domain.valueobjects.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Scrim {

    private final UUID id;
    private final Juego juego;
    private final FormatoScrim formato;
    private final Region region;
    private final RangosPermitidos rangosPermitidos;
    private final Latencia latenciaMax;
    private final LocalDateTime fechaHora;
    private final Duration duracionEstimada;
    private final Modalidad modalidad;
    private final Map<RolJuego, Integer> reglasRoles;
    private final UUID organizadorId;
    private final List<Equipo> equipos;
    private final List<Confirmacion> confirmaciones;
    private ScrimState currentState;
    private String motivoCancelacion;
    private final List<DomainEvent> domainEvents;

    public Scrim(UUID id, Juego juego, FormatoScrim formato, Region region,
          RangosPermitidos rangosPermitidos, Latencia latenciaMax,
          LocalDateTime fechaHora, Duration duracionEstimada,
          Modalidad modalidad, Map<RolJuego, Integer> reglasRoles,
          UUID organizadorId) {
        this.id = id;
        this.juego = juego;
        this.formato = formato;
        this.region = region;
        this.rangosPermitidos = rangosPermitidos;
        this.latenciaMax = latenciaMax;
        this.fechaHora = fechaHora;
        this.duracionEstimada = duracionEstimada;
        this.modalidad = modalidad;
        this.reglasRoles = Collections.unmodifiableMap(new HashMap<>(reglasRoles));
        this.organizadorId = organizadorId;
        this.equipos = new ArrayList<>(List.of(
                new Equipo(LadoEquipo.EQUIPO_A),
                new Equipo(LadoEquipo.EQUIPO_B)
        ));
        this.confirmaciones = new ArrayList<>();
        this.domainEvents = new ArrayList<>();
        this.currentState = new BuscandoJugadoresState();
        this.domainEvents.add(new ScrimCreadoEvent(id, juego.getNombre(), region, formato, fechaHora));
    }

    public void agregarJugador(UUID usuarioId, LadoEquipo lado, RolJuego rol) {
        if (!(currentState instanceof BuscandoJugadoresState)) {
            throw new IllegalStateException("Solo se pueden agregar jugadores mientras se buscan participantes");
        }
        agregarParticipanteInterno(usuarioId, lado, rol);
        if (estaCompleto()) {
            currentState.avanzar(this);
        }
    }

    public void confirmarParticipacion(UUID usuarioId) {
        if (!(currentState instanceof LobbyArmadoState)) {
            throw new IllegalStateException("Solo se puede confirmar con el lobby armado");
        }
        confirmarParticipanteInterno(usuarioId);
        if (todosConfirmaron()) {
            currentState.avanzar(this);
        }
    }

    public void iniciar() {
        if (!(currentState instanceof com.escrims.domain.state.ConfirmadoState)) {
            throw new IllegalStateException("Solo se puede iniciar un scrim confirmado");
        }
        currentState.avanzar(this);
    }

    public void finalizar() {
        if (!(currentState instanceof com.escrims.domain.state.EnJuegoState)) {
            throw new IllegalStateException("Solo se puede finalizar un scrim en juego");
        }
        currentState.avanzar(this);
    }

    public void cancelar(String motivo) {
        this.motivoCancelacion = motivo;
        currentState.cancelar(this);
    }

    public void avanzarEstado() {
        currentState.avanzar(this);
    }

    public String getEstadoNombre() { return currentState.getNombreEstado(); }

    public boolean estaCompleto() {
        return equipos.stream().allMatch(e -> e.estaCompleto(formato.getJugadoresPorLado()));
    }

    public boolean todosConfirmaron() {
        return !confirmaciones.isEmpty()
                && confirmaciones.stream().noneMatch(Confirmacion::esPendiente);
    }

    public boolean permiteModificarRoles() {
        return currentState instanceof BuscandoJugadoresState
                || currentState instanceof LobbyArmadoState;
    }

    public int getCuposDisponibles() {
        int ocupados = equipos.stream().mapToInt(Equipo::getCuposOcupados).sum();
        return formato.getTotalJugadores() - ocupados;
    }

    public List<UUID> getParticipantesIds() {
        return equipos.stream()
                .flatMap(e -> e.getParticipanteIds().stream())
                .collect(Collectors.toList());
    }

    public void cambiarEstado(ScrimState nuevoEstado) { this.currentState = nuevoEstado; }
    public void agregarEvento(DomainEvent event) { this.domainEvents.add(event); }

    void agregarParticipanteInterno(UUID usuarioId, LadoEquipo lado, RolJuego rol) {
        equipos.stream().filter(e -> e.getLado().equals(lado)).findFirst()
               .ifPresent(e -> e.agregarParticipante(usuarioId, rol));
        confirmaciones.add(new Confirmacion(usuarioId));
    }

    void confirmarParticipanteInterno(UUID usuarioId) {
        confirmaciones.stream()
                .filter(c -> c.getUsuarioId().equals(usuarioId) && c.esPendiente())
                .findFirst().ifPresent(Confirmacion::confirmar);
    }

    public List<DomainEvent> recolectarEventos() {
        List<DomainEvent> eventos = new ArrayList<>(domainEvents);
        domainEvents.clear();
        return eventos;
    }

    public UUID getId() { return id; }
    public Juego getJuego() { return juego; }
    public String getNombreJuego() { return juego.getNombre(); }
    public FormatoScrim getFormato() { return formato; }
    public Region getRegion() { return region; }
    public RangosPermitidos getRangosPermitidos() { return rangosPermitidos; }
    public Latencia getLatenciaMax() { return latenciaMax; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public Duration getDuracionEstimada() { return duracionEstimada; }
    public Modalidad getModalidad() { return modalidad; }
    public Map<RolJuego, Integer> getReglasRoles() { return reglasRoles; }
    public UUID getOrganizadorId() { return organizadorId; }
    public List<Equipo> getEquipos() { return Collections.unmodifiableList(equipos); }
    public List<Confirmacion> getConfirmaciones() { return Collections.unmodifiableList(confirmaciones); }
    public String getMotivoCancelacion() { return motivoCancelacion != null ? motivoCancelacion : ""; }
    public ScrimState getCurrentState() { return currentState; }
}
