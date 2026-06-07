package com.escrims.infrastructure.scheduling;

import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.repository.ScrimRepository;
import com.escrims.domain.services.ScrimLifecycleService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ScrimScheduler {

    private final ScrimLifecycleService lifecycleService;
    private final ScrimRepository scrimRepository;

    public ScrimScheduler(ScrimLifecycleService lifecycleService, ScrimRepository scrimRepository) {
        this.lifecycleService = lifecycleService;
        this.scrimRepository = scrimRepository;
    }

    @Scheduled(fixedDelayString = "${escrims.scheduler.delay-ms:60000}")
    public void procesarScrimsConfirmados() {
        List<Scrim> confirmados = scrimRepository.findByEstado("CONFIRMADO");
        confirmados.stream()
                .filter(s -> !s.getFechaHora().isAfter(LocalDateTime.now()))
                .forEach(s -> lifecycleService.iniciar(s.getId()));
    }

    @Scheduled(fixedDelayString = "${escrims.scheduler.delay-ms:60000}")
    public void procesarScrimsEnJuego() {
        List<Scrim> enJuego = scrimRepository.findByEstado("EN_JUEGO");
        enJuego.stream()
                .filter(s -> {
                    LocalDateTime fin = s.getFechaHora().plus(s.getDuracionEstimada());
                    return !fin.isAfter(LocalDateTime.now());
                })
                .forEach(s -> lifecycleService.finalizar(s.getId()));
    }
}
