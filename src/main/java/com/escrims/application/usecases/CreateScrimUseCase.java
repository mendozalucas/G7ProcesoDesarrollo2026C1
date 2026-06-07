package com.escrims.application.usecases;

import com.escrims.application.dto.CreateScrimDTO;
import com.escrims.domain.builder.ScrimBuilder;
import com.escrims.domain.model.juego.JuegoFactory;
import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.observer.DomainEventBus;
import com.escrims.domain.repository.ScrimRepository;
import com.escrims.domain.valueobjects.FormatoScrim;
import com.escrims.domain.valueobjects.Latencia;
import com.escrims.domain.valueobjects.Region;

import java.time.Duration;
import java.util.UUID;

public class CreateScrimUseCase {

    private final ScrimRepository scrimRepository;
    private final DomainEventBus eventBus;

    public CreateScrimUseCase(ScrimRepository scrimRepository, DomainEventBus eventBus) {
        this.scrimRepository = scrimRepository;
        this.eventBus = eventBus;
    }

    public UUID execute(CreateScrimDTO dto) {
        Scrim scrim = new ScrimBuilder()
                .conJuego(JuegoFactory.para(dto.getJuego()))
                .conFormato(new FormatoScrim(dto.getJugadoresPorLado()))
                .conRegion(new Region(dto.getServidor(), dto.getZona()))
                .conRango(dto.getRangoMin(), dto.getRangoMax())
                .conLatenciaMax(new Latencia(dto.getLatenciaMaxMs()))
                .conFechaHora(dto.getFechaHora())
                .conDuracion(Duration.ofMinutes(dto.getDuracionMinutos()))
                .enModalidad(dto.getModalidad())
                .creadoPor(dto.getOrganizadorId())
                .build();

        scrimRepository.save(scrim);
        eventBus.publicarTodos(scrim.recolectarEventos());
        return scrim.getId();
    }
}
