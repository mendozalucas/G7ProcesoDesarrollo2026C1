package com.escrims.domain.services;

import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.observer.DomainEventBus;
import com.escrims.domain.repository.ScrimRepository;
import com.escrims.domain.state.ScrimState;

import java.util.UUID;

public class ScrimLifecycleService {

    private ScrimState estado;
    private final ScrimRepository scrimRepository;
    private final DomainEventBus eventBus;

    public ScrimLifecycleService(ScrimRepository scrimRepository, DomainEventBus eventBus) {
        this.scrimRepository = scrimRepository;
        this.eventBus = eventBus;
    }

    public void cambiarEstado(Scrim scrim, ScrimState nuevoEstado) {
        scrim.cambiarEstado(nuevoEstado);
        this.estado = nuevoEstado;
        scrimRepository.save(scrim);
    }

    public void iniciarScrim(UUID scrimId) { iniciar(scrimId); }
    public void finalizarScrim(UUID scrimId) { finalizar(scrimId); }
    public void cancelarScrim(UUID scrimId, String motivo) { cancelar(scrimId, motivo); }

    public void iniciar(UUID scrimId) {
        Scrim scrim = cargar(scrimId);
        scrim.iniciar();
        this.estado = scrim.getCurrentState();
        scrimRepository.save(scrim);
        eventBus.publicarTodos(scrim.recolectarEventos());
    }

    public void finalizar(UUID scrimId) {
        Scrim scrim = cargar(scrimId);
        scrim.finalizar();
        this.estado = scrim.getCurrentState();
        scrimRepository.save(scrim);
        eventBus.publicarTodos(scrim.recolectarEventos());
    }

    public void cancelar(UUID scrimId, String motivo) {
        Scrim scrim = cargar(scrimId);
        scrim.cancelar(motivo);
        this.estado = scrim.getCurrentState();
        scrimRepository.save(scrim);
        eventBus.publicarTodos(scrim.recolectarEventos());
    }

    public ScrimState getEstado() { return estado; }

    private Scrim cargar(UUID id) {
        return scrimRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Scrim no encontrado: " + id));
    }
}
