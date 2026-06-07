package com.escrims.application.usecases;

import com.escrims.domain.services.ScrimLifecycleService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CancelScrimUseCase {

    private final ScrimLifecycleService lifecycleService;

    public CancelScrimUseCase(ScrimLifecycleService lifecycleService) {
        this.lifecycleService = lifecycleService;
    }

    public void execute(UUID scrimId, String motivo) {
        lifecycleService.cancelar(scrimId, motivo);
    }
}
