package com.escrims.application.usecases;

import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.repository.ScrimRepository;
import com.escrims.domain.services.ScrimLifecycleService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CancelScrimUseCase {

    private final ScrimLifecycleService lifecycleService;
    private final ScrimRepository scrimRepository;

    public CancelScrimUseCase(ScrimLifecycleService lifecycleService, ScrimRepository scrimRepository) {
        this.lifecycleService = lifecycleService;
        this.scrimRepository = scrimRepository;
    }

    public void execute(UUID scrimId, String motivo) {
        Scrim scrim = scrimRepository.findById(scrimId)
                .orElseThrow(() -> new IllegalArgumentException("Scrim no encontrado: " + scrimId));
        lifecycleService.cancelar(scrim, motivo);
        scrimRepository.save(scrim);
    }
}
