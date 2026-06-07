package com.escrims.infrastructure.notifications.observers;

import com.escrims.domain.events.DomainEvent;
import com.escrims.domain.events.DomainEventVisitor;
import com.escrims.domain.events.NuevoScrimDisponibleEvent;
import com.escrims.domain.events.ScrimCreadoEvent;
import com.escrims.domain.model.busqueda.BusquedaFavorita;
import com.escrims.domain.observer.DomainEventBus;
import com.escrims.domain.observer.IObserver;
import com.escrims.domain.repository.BusquedaFavoritaRepository;
import com.escrims.domain.repository.ScrimRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class AlertaBusquedaObserver implements IObserver, DomainEventVisitor {

    private final List<BusquedaFavorita> busquedasFavoritas;
    private final BusquedaFavoritaRepository busquedaRepository;
    private final ScrimRepository scrimRepository;
    private final DomainEventBus eventBus;

    public AlertaBusquedaObserver(BusquedaFavoritaRepository busquedaRepository,
                                   ScrimRepository scrimRepository,
                                   DomainEventBus eventBus) {
        this.busquedaRepository = busquedaRepository;
        this.scrimRepository = scrimRepository;
        this.eventBus = eventBus;
        this.busquedasFavoritas = busquedaRepository.findAlertasActivas();
    }

    @Override
    public void onEvent(DomainEvent event) {
        event.aceptar(this);
    }

    @Override
    public void visit(ScrimCreadoEvent e) {
        scrimRepository.findById(e.getScrimId()).ifPresent(scrim -> {
            List<UUID> usuarios = busquedasFavoritas.stream()
                    .filter(BusquedaFavorita::isAlertaActiva)
                    .filter(b -> b.coincideCon(scrim))
                    .map(BusquedaFavorita::getUsuarioId)
                    .collect(Collectors.toList());
            if (usuarios.isEmpty()) {
                usuarios = busquedaRepository.findAlertasActivas().stream()
                        .filter(b -> b.coincideCon(scrim))
                        .map(BusquedaFavorita::getUsuarioId)
                        .collect(Collectors.toList());
            }
            if (!usuarios.isEmpty()) {
                eventBus.publish(new NuevoScrimDisponibleEvent(e.getScrimId(), usuarios));
            }
        });
    }
}
