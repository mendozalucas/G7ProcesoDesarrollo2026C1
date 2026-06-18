package com.escrims.application.usecases;

import com.escrims.domain.model.postulacion.Postulacion;
import com.escrims.domain.observer.DomainEventBus;
import com.escrims.domain.repository.PostulacionRepository;
import org.springframework.stereotype.Service;

@Service
public class AcceptPostulacionUseCase {

    private final PostulacionRepository postulacionRepository;
    private final DomainEventBus eventBus;

    public AcceptPostulacionUseCase(PostulacionRepository postulacionRepository,
                                    DomainEventBus eventBus) {
        this.postulacionRepository = postulacionRepository;
        this.eventBus = eventBus;
    }

    public void execute(Long postulacionId) {
        Postulacion postulacion = postulacionRepository.findById(postulacionId)
                .orElseThrow(() -> new IllegalArgumentException("Postulación no encontrada: " + postulacionId));
        postulacion.aceptar();
        postulacionRepository.save(postulacion);
        postulacion.getScrim().recolectarEventos().forEach(eventBus::publish);
    }
}
