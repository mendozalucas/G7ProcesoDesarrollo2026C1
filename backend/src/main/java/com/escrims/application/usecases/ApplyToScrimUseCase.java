package com.escrims.application.usecases;

import com.escrims.domain.model.postulacion.EstadoPostulacion;
import com.escrims.domain.model.postulacion.Postulacion;
import com.escrims.domain.model.rol.Rol;
import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.model.usuario.Jugador;
import com.escrims.domain.model.usuario.Usuario;
import com.escrims.domain.observer.DomainEventBus;
import com.escrims.domain.repository.PostulacionRepository;
import com.escrims.domain.repository.ScrimRepository;
import com.escrims.domain.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApplyToScrimUseCase {

    private final PostulacionRepository postulacionRepository;
    private final ScrimRepository scrimRepository;
    private final UsuarioRepository usuarioRepository;
    private final DomainEventBus eventBus;

    public ApplyToScrimUseCase(PostulacionRepository postulacionRepository,
                               ScrimRepository scrimRepository,
                               UsuarioRepository usuarioRepository,
                               DomainEventBus eventBus) {
        this.postulacionRepository = postulacionRepository;
        this.scrimRepository = scrimRepository;
        this.usuarioRepository = usuarioRepository;
        this.eventBus = eventBus;
    }

    public Long execute(UUID usuarioId, UUID scrimId, String rolNombre) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + usuarioId));
        if (!(usuario instanceof Jugador jugador)) {
            throw new IllegalStateException("Solo un jugador puede postularse a un scrim");
        }
        Scrim scrim = scrimRepository.findById(scrimId)
                .orElseThrow(() -> new IllegalArgumentException("Scrim no encontrado: " + scrimId));

        Rol rol = new Rol(null, rolNombre);
        Postulacion postulacion = new Postulacion(null, jugador, scrim, rol, EstadoPostulacion.PENDIENTE);
        Postulacion guardada = postulacionRepository.save(postulacion);
        scrim.recolectarEventos().forEach(eventBus::publish);
        return guardada.getId();
    }
}
