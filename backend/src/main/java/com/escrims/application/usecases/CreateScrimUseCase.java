package com.escrims.application.usecases;

import com.escrims.application.dto.CreateScrimDTO;
import com.escrims.domain.builder.ScrimBuilder;
import com.escrims.domain.model.juego.JuegoFactory;
import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.model.usuario.Usuario;
import com.escrims.domain.observer.DomainEventBus;
import com.escrims.domain.repository.ScrimRepository;
import com.escrims.domain.repository.UsuarioRepository;
import com.escrims.domain.valueobjects.Region;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateScrimUseCase {

    private final ScrimRepository scrimRepository;
    private final UsuarioRepository usuarioRepository;
    private final DomainEventBus eventBus;

    public CreateScrimUseCase(ScrimRepository scrimRepository,
                              UsuarioRepository usuarioRepository,
                              DomainEventBus eventBus) {
        this.scrimRepository = scrimRepository;
        this.usuarioRepository = usuarioRepository;
        this.eventBus = eventBus;
    }

    public UUID execute(CreateScrimDTO dto) {
        Usuario organizador = usuarioRepository.findById(dto.getOrganizadorId())
                .orElseThrow(() -> new IllegalArgumentException("Organizador no encontrado: " + dto.getOrganizadorId()));

        String regionNombre = dto.getServidor();
        if (dto.getZona() != null && !dto.getZona().isBlank()) {
            regionNombre = dto.getServidor() + "/" + dto.getZona();
        }

        Scrim scrim = new ScrimBuilder()
                .conJuego(JuegoFactory.para(dto.getJuego()))
                .conRegion(new Region(null, regionNombre))
                .conRango(dto.getRangoMin(), dto.getRangoMax())
                .conLatenciaMax(dto.getLatenciaMaxMs())
                .conFechaHora(dto.getFechaHora())
                .creadoPor(organizador)
                .build();

        scrimRepository.save(scrim);
        scrim.recolectarEventos().forEach(eventBus::publish);
        return scrim.getId();
    }
}
