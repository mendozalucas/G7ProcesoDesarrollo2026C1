package com.escrims.application.usecases;

import com.escrims.application.dto.ScrimResponseDTO;
import com.escrims.domain.repository.ScrimRepository;
import com.escrims.domain.valueobjects.CriteriosBusqueda;
import com.escrims.domain.valueobjects.Latencia;
import com.escrims.domain.valueobjects.Region;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchScrimsUseCase {

    private final ScrimRepository scrimRepository;

    public SearchScrimsUseCase(ScrimRepository scrimRepository) {
        this.scrimRepository = scrimRepository;
    }

    public List<ScrimResponseDTO> execute(String juego,
                                           Integer jugadoresPorLado,
                                           String servidor,
                                           String zona,
                                           LocalDateTime desde,
                                           LocalDateTime hasta,
                                           Integer latenciaMaxMs) {
        String regionNombre = servidor;
        if (zona != null && !zona.isBlank() && servidor != null) {
            regionNombre = servidor + "/" + zona;
        }
        Region region = servidor != null ? new Region(null, regionNombre) : null;
        Latencia latencia = latenciaMaxMs != null ? new Latencia(latenciaMaxMs) : null;

        CriteriosBusqueda criterios = new CriteriosBusqueda(
                juego, null, null, region, desde, hasta, latencia);

        return scrimRepository.findByCriteria(criterios).stream()
                .map(ScrimResponseDTO::from)
                .collect(Collectors.toList());
    }
}
