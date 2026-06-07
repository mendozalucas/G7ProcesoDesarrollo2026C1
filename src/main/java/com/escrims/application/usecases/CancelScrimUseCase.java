package com.escrims.application.usecases;

import com.escrims.domain.services.ScrimLifecycleService;

import java.util.UUID;

public class CancelScrimUseCase {

    private final ScrimLifecycleService lifecycleService;

    public CancelScrimUseCase(ScrimLifecycleService lifecycleService) {
        this.lifecycleService = lifecycleService;
    }

    public void execute(UUID scrimId, String motivo) {
        lifecycleService.cancelar(scrimId, motivo);
    }
}
