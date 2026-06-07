package com.escrims.domain.model.scrim;

import com.escrims.domain.model.usuario.Usuario;
import com.escrims.domain.valueobjects.LadoEquipo;
import com.escrims.domain.valueobjects.RolJuego;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/** Composite del patrón Composite (ver Ejemplos/CompositeExercise). */
public class Equipo implements JugadorComponent {

    private final UUID id;
    private final String nombre;
    private final LadoEquipo lado;
    private final List<JugadorComponent> jugadores;
    private UUID capitanId;

    public Equipo(LadoEquipo lado) {
        this.id = UUID.randomUUID();
        this.lado = lado;
        this.nombre = lado.getNombre();
        this.jugadores = new ArrayList<>();
    }

    public void agregarJugador(Usuario usuario) {
        agregar(new Participante(usuario.getId(), null));
    }

    public void quitarJugador(Usuario usuario) {
        quitar(usuario.getId());
    }

    public void agregarParticipante(UUID usuarioId, RolJuego rol) {
        if (jugadores.stream().anyMatch(j -> j.getUsuarioId().equals(usuarioId))) {
            throw new IllegalStateException("El jugador ya está en el equipo");
        }
        jugadores.add(new Participante(usuarioId, rol));
    }

    public void removerParticipante(UUID usuarioId) {
        quitar(usuarioId);
    }

    @Override
    public void agregar(JugadorComponent componente) {
        if (jugadores.stream().anyMatch(j -> j.getUsuarioId().equals(componente.getUsuarioId()))) {
            throw new IllegalStateException("El jugador ya está en el equipo");
        }
        jugadores.add(componente);
    }

    @Override
    public void quitar(UUID usuarioId) {
        jugadores.removeIf(j -> j.getUsuarioId().equals(usuarioId));
    }

    @Override
    public int contarJugadores() {
        return jugadores.stream().mapToInt(JugadorComponent::contarJugadores).sum();
    }

    @Override
    public UUID getUsuarioId() {
        throw new UnsupportedOperationException("Un equipo no representa un único jugador");
    }

    @Override
    public RolJuego getRolAsignado() {
        throw new UnsupportedOperationException("Un equipo no tiene un rol asignado");
    }

    @Override
    public List<JugadorComponent> getHijos() {
        return Collections.unmodifiableList(jugadores);
    }

    public void asignarRol(UUID usuarioId, RolJuego nuevoRol) {
        for (int i = 0; i < jugadores.size(); i++) {
            JugadorComponent actual = jugadores.get(i);
            if (actual.getUsuarioId().equals(usuarioId)) {
                jugadores.set(i, new Participante(usuarioId, nuevoRol));
                return;
            }
        }
        throw new IllegalArgumentException("Jugador no encontrado en el equipo");
    }

    public void swapParticipantes(UUID u1Id, UUID u2Id) {
        Optional<Participante> p1 = getParticipante(u1Id);
        Optional<Participante> p2 = getParticipante(u2Id);
        if (p1.isEmpty() || p2.isEmpty()) {
            throw new IllegalArgumentException("Uno o ambos jugadores no están en el equipo");
        }
        asignarRol(u1Id, p2.get().getRolAsignado());
        asignarRol(u2Id, p1.get().getRolAsignado());
    }

    public boolean estaCompleto(int cuposPorLado) {
        return contarJugadores() >= cuposPorLado;
    }

    public Optional<Participante> getParticipante(UUID usuarioId) {
        return jugadores.stream()
                .filter(j -> j.getUsuarioId().equals(usuarioId))
                .findFirst()
                .map(j -> new Participante(j.getUsuarioId(), j.getRolAsignado()));
    }

    public List<Participante> getParticipantes() {
        return jugadores.stream()
                .map(j -> new Participante(j.getUsuarioId(), j.getRolAsignado()))
                .collect(Collectors.toList());
    }

    public List<UUID> getParticipanteIds() {
        return jugadores.stream().map(JugadorComponent::getUsuarioId).collect(Collectors.toList());
    }

    public UUID getId() { return id; }
    public String getNombre() { return nombre; }
    public LadoEquipo getLado() { return lado; }
    public int getCuposOcupados() { return contarJugadores(); }
    public UUID getCapitanId() { return capitanId; }
    public void setCapitanId(UUID capitanId) { this.capitanId = capitanId; }
}
