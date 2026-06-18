package com.escrims.application.usecases;

import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.model.usuario.Usuario;
import com.escrims.domain.observer.DomainEventBus;
import com.escrims.domain.repository.ScrimRepository;
import com.escrims.domain.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ConfirmParticipationUseCase {

    private final UsuarioRepository usuarioRepository;
    private final ScrimRepository scrimRepository;
    private final DomainEventBus eventBus;

    public ConfirmParticipationUseCase(UsuarioRepository usuarioRepository,
                                       ScrimRepository scrimRepository,
                                       DomainEventBus eventBus) {
        this.usuarioRepository = usuarioRepository;
        this.scrimRepository = scrimRepository;
        this.eventBus = eventBus;
    }

    public void execute(UUID usuarioId, UUID scrimId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + usuarioId));
        Scrim scrim = scrimRepository.findById(scrimId)
                .orElseThrow(() -> new IllegalArgumentException("Scrim no encontrado: " + scrimId));

        usuario.confirmar(scrim);
        scrim.recolectarEventos().forEach(eventBus::publish);
    }
}
