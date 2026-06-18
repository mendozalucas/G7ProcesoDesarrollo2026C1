package com.escrims.application.facade;

import com.escrims.domain.facade.ScrimFacadePort;
import com.escrims.application.dto.CreateScrimDTO;
import com.escrims.application.dto.EstadisticaDTO;
import com.escrims.application.usecases.ApplyToScrimUseCase;
import com.escrims.application.usecases.ConfirmParticipationUseCase;
import com.escrims.application.usecases.CreateScrimUseCase;
import com.escrims.application.usecases.FinalizeScrimUseCase;
import com.escrims.domain.model.postulacion.EstadoPostulacion;
import com.escrims.domain.model.postulacion.Postulacion;
import com.escrims.domain.model.rol.Rol;
import com.escrims.domain.model.scrim.Confirmacion;
import com.escrims.domain.model.scrim.CreateScrimRequest;
import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.model.usuario.Jugador;
import com.escrims.domain.repository.ScrimRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ScrimFacade implements ScrimFacadePort {

    private final CreateScrimUseCase createScrimUseCase;
    private final ApplyToScrimUseCase applyToScrimUseCase;
    private final ConfirmParticipationUseCase confirmParticipationUseCase;
    private final FinalizeScrimUseCase finalizeScrimUseCase;
    private final ScrimRepository scrimRepository;

    public ScrimFacade(CreateScrimUseCase createScrimUseCase,
                       ApplyToScrimUseCase applyToScrimUseCase,
                       ConfirmParticipationUseCase confirmParticipationUseCase,
                       FinalizeScrimUseCase finalizeScrimUseCase,
                       ScrimRepository scrimRepository) {
        this.createScrimUseCase = createScrimUseCase;
        this.applyToScrimUseCase = applyToScrimUseCase;
        this.confirmParticipationUseCase = confirmParticipationUseCase;
        this.finalizeScrimUseCase = finalizeScrimUseCase;
        this.scrimRepository = scrimRepository;
    }

    @Override
    public Postulacion postularse(Jugador jugador, Scrim scrim, Rol rol) {
        Long postulacionId = applyToScrimUseCase.execute(jugador.getId(), scrim.getId(), rol.getNombre());
        return new Postulacion(postulacionId, jugador, scrim, rol, EstadoPostulacion.PENDIENTE);
    }

    @Override
    public Confirmacion confirmarJugador(Jugador jugador, Scrim scrim) {
        confirmParticipationUseCase.execute(jugador.getId(), scrim.getId());
        return new Confirmacion(null, jugador, scrim, true);
    }

    @Override
    public Scrim crearScrim(Jugador organizador, CreateScrimRequest request) {
        return crearScrim(organizador, toDto(organizador, request));
    }

    public Scrim crearScrim(Jugador organizador, CreateScrimDTO request) {
        request.setOrganizadorId(organizador.getId());
        UUID scrimId = createScrimUseCase.execute(request);
        return scrimRepository.findById(scrimId)
                .orElseThrow(() -> new IllegalStateException("Scrim recién creado no encontrado: " + scrimId));
    }

    public void cargarResultados(Jugador organizador, Scrim scrim, List<EstadisticaDTO> estadisticas) {
        if (!scrim.getOrganizador().getId().equals(organizador.getId())) {
            throw new IllegalStateException("Solo el organizador puede cargar resultados");
        }
        finalizeScrimUseCase.execute(scrim.getId(), estadisticas);
    }

    private CreateScrimDTO toDto(Jugador organizador, CreateScrimRequest request) {
        CreateScrimDTO dto = new CreateScrimDTO();
        dto.setOrganizadorId(organizador.getId());
        dto.setJuego(request.getJuego());
        if (request.getFormato() != null) {
            dto.setJugadoresPorLado(request.getFormato().getJugadoresPorLado());
        }
        dto.setModalidadNombre(request.getModalidad());
        if (request.getRegion() != null) {
            String nombre = request.getRegion().getNombre();
            int slash = nombre.indexOf('/');
            if (slash >= 0) {
                dto.setServidor(nombre.substring(0, slash));
                dto.setZona(nombre.substring(slash + 1));
            } else {
                dto.setServidor(nombre);
            }
        }
        dto.setRangoMin(request.getRangoMin());
        dto.setRangoMax(request.getRangoMax());
        dto.setLatenciaMaxMs(request.getLatenciaMaxMs());
        dto.setFechaHora(request.getFechaHora());
        dto.setDuracionMinutos(request.getDuracionMinutos());
        return dto;
    }
}
