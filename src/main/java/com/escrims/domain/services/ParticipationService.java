package com.escrims.domain.services;

import com.escrims.domain.command.AsignarRolCommand;
import com.escrims.domain.command.ScrimCommandHistory;
import com.escrims.domain.command.SwapJugadoresCommand;
import com.escrims.domain.model.postulacion.Postulacion;
import com.escrims.domain.model.scrim.Equipo;
import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.model.usuario.Usuario;
import com.escrims.domain.observer.DomainEventBus;
import com.escrims.domain.repository.PostulacionRepository;
import com.escrims.domain.repository.ScrimRepository;
import com.escrims.domain.repository.UsuarioRepository;
import com.escrims.domain.valueobjects.LadoEquipo;
import com.escrims.domain.valueobjects.RolJuego;

import java.util.UUID;

public class ParticipationService {

    private final ScrimRepository scrimRepository;
    private final PostulacionRepository postulacionRepository;
    private final UsuarioRepository usuarioRepository;
    private final DomainEventBus eventBus;
    private final ScrimCommandHistory commandHistory;

    public ParticipationService(ScrimRepository scrimRepository,
                                 PostulacionRepository postulacionRepository,
                                 DomainEventBus eventBus) {
        this(scrimRepository, postulacionRepository, null, eventBus, new ScrimCommandHistory());
    }

    public ParticipationService(ScrimRepository scrimRepository,
                                 PostulacionRepository postulacionRepository,
                                 UsuarioRepository usuarioRepository,
                                 DomainEventBus eventBus) {
        this(scrimRepository, postulacionRepository, usuarioRepository, eventBus, new ScrimCommandHistory());
    }

    public ParticipationService(ScrimRepository scrimRepository,
                                 PostulacionRepository postulacionRepository,
                                 UsuarioRepository usuarioRepository,
                                 DomainEventBus eventBus,
                                 ScrimCommandHistory commandHistory) {
        this.scrimRepository = scrimRepository;
        this.postulacionRepository = postulacionRepository;
        this.usuarioRepository = usuarioRepository;
        this.eventBus = eventBus;
        this.commandHistory = commandHistory;
    }

    public UUID postularseAScrim(UUID usuarioId, UUID scrimId, RolJuego rol) {
        Postulacion p = new Postulacion(usuarioId, scrimId, rol);
        postulacionRepository.save(p);
        return p.getId();
    }

    public void aceptarPostulacion(UUID postulacionId, LadoEquipo lado) {
        Postulacion p = postulacionRepository.findById(postulacionId)
                .orElseThrow(() -> new IllegalArgumentException("Postulación no encontrada"));
        p.aceptar();
        postulacionRepository.save(p);

        Scrim scrim = scrimRepository.findById(p.getScrimId())
                .orElseThrow(() -> new IllegalArgumentException("Scrim no encontrado"));
        scrim.agregarJugador(p.getUsuarioId(), lado, p.getRolDeseado());
        scrimRepository.save(scrim);

        eventBus.publicarTodos(p.recolectarEventos());
        eventBus.publicarTodos(scrim.recolectarEventos());
    }

    public void rechazarPostulacion(UUID postulacionId) {
        Postulacion p = postulacionRepository.findById(postulacionId)
                .orElseThrow(() -> new IllegalArgumentException("Postulación no encontrada"));
        p.rechazar();
        postulacionRepository.save(p);
        eventBus.publicarTodos(p.recolectarEventos());
    }

    public void confirmarParticipacion(UUID usuarioId, UUID scrimId) {
        Scrim scrim = scrimRepository.findById(scrimId)
                .orElseThrow(() -> new IllegalArgumentException("Scrim no encontrado"));
        scrim.confirmarParticipacion(usuarioId);
        scrimRepository.save(scrim);
        eventBus.publicarTodos(scrim.recolectarEventos());
    }

    public void asignarRol(UUID scrimId, UUID usuarioId, LadoEquipo lado, RolJuego rol) {
        Scrim scrim = scrimRepository.findById(scrimId)
                .orElseThrow(() -> new IllegalArgumentException("Scrim no encontrado"));
        Usuario usuario = requireUsuario(usuarioId);
        commandHistory.ejecutar(new AsignarRolCommand(usuario, lado, rol), scrim);
        scrimRepository.save(scrim);
    }

    public void deshacerUltimoComando(UUID scrimId) {
        Scrim scrim = scrimRepository.findById(scrimId)
                .orElseThrow(() -> new IllegalArgumentException("Scrim no encontrado"));
        commandHistory.deshacer(scrim);
        scrimRepository.save(scrim);
    }

    public void swapJugadores(UUID scrimId, UUID u1, UUID u2) {
        Scrim scrim = scrimRepository.findById(scrimId)
                .orElseThrow(() -> new IllegalArgumentException("Scrim no encontrado"));
        for (Equipo equipo : scrim.getEquipos()) {
            boolean tieneAmbos = equipo.getParticipante(u1).isPresent()
                    && equipo.getParticipante(u2).isPresent();
            if (tieneAmbos) {
                swapJugadores(scrimId, u1, u2, equipo.getLado());
                return;
            }
        }
        throw new IllegalArgumentException("Los jugadores no comparten equipo");
    }

    public void swapJugadores(UUID scrimId, UUID u1, UUID u2, LadoEquipo lado) {
        Scrim scrim = scrimRepository.findById(scrimId)
                .orElseThrow(() -> new IllegalArgumentException("Scrim no encontrado"));
        Usuario jugador1 = requireUsuario(u1);
        Usuario jugador2 = requireUsuario(u2);
        commandHistory.ejecutar(new SwapJugadoresCommand(jugador1, jugador2, lado), scrim);
        scrimRepository.save(scrim);
    }

    private Usuario requireUsuario(UUID usuarioId) {
        if (usuarioRepository == null) {
            throw new IllegalStateException("UsuarioRepository requerido para esta operación");
        }
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    }
}
