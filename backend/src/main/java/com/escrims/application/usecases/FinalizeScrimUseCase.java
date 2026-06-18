package com.escrims.application.usecases;

import com.escrims.application.dto.EstadisticaDTO;
import com.escrims.domain.model.estadistica.Estadistica;
import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.model.usuario.Usuario;
import com.escrims.domain.repository.EstadisticaRepository;
import com.escrims.domain.repository.ScrimRepository;
import com.escrims.domain.repository.UsuarioRepository;
import com.escrims.domain.services.ScrimLifecycleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FinalizeScrimUseCase {

    private final ScrimLifecycleService lifecycleService;
    private final ScrimRepository scrimRepository;
    private final EstadisticaRepository estadisticaRepository;
    private final UsuarioRepository usuarioRepository;

    public FinalizeScrimUseCase(ScrimLifecycleService lifecycleService,
                                ScrimRepository scrimRepository,
                                EstadisticaRepository estadisticaRepository,
                                UsuarioRepository usuarioRepository) {
        this.lifecycleService = lifecycleService;
        this.scrimRepository = scrimRepository;
        this.estadisticaRepository = estadisticaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public void execute(UUID scrimId, List<EstadisticaDTO> estadisticas) {
        Scrim scrim = scrimRepository.findById(scrimId)
                .orElseThrow(() -> new IllegalArgumentException("Scrim no encontrado: " + scrimId));
        lifecycleService.finalizar(scrim);
        scrimRepository.save(scrim);

        for (EstadisticaDTO dto : estadisticas) {
            Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + dto.getUsuarioId()));
            Estadistica estadistica = new Estadistica(
                    null,
                    usuario,
                    dto.getKills(),
                    dto.getDeaths(),
                    dto.getAssists(),
                    dto.getObservaciones());
            estadisticaRepository.save(estadistica);
        }
    }
}
