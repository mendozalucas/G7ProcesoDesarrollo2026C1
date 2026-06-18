package com.escrims.application.usecases;

import com.escrims.application.dto.CreateScrimDTO;
import com.escrims.domain.builder.ScrimBuilder;
import com.escrims.domain.model.juego.JuegoFactory;
import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.model.usuario.Jugador;
import com.escrims.domain.model.usuario.Usuario;
import com.escrims.domain.observer.DomainEventBus;
import com.escrims.domain.repository.ScrimRepository;
import com.escrims.domain.repository.UsuarioRepository;
import com.escrims.domain.valueobjects.FormatoScrim;
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
        Usuario organizadorUsuario = usuarioRepository.findById(dto.getOrganizadorId())
                .orElseThrow(() -> new IllegalArgumentException("Organizador no encontrado: " + dto.getOrganizadorId()));
        if (!(organizadorUsuario instanceof Jugador organizador)) {
            throw new IllegalArgumentException("El organizador debe ser un jugador");
        }

        String regionNombre = dto.getServidor();
        if (dto.getZona() != null && !dto.getZona().isBlank()) {
            regionNombre = dto.getServidor() + "/" + dto.getZona();
        }

        String modalidad = dto.getModalidadNombre();
        if (modalidad == null && dto.getModalidad() != null) {
            modalidad = dto.getModalidad().getNombre();
        }
        if (modalidad == null) {
            modalidad = "CASUAL";
        }

        Scrim scrim = new ScrimBuilder()
                .conJuego(JuegoFactory.para(dto.getJuego()))
                .conFormato(new FormatoScrim(dto.getJugadoresPorLado()))
                .conModalidad(modalidad)
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
