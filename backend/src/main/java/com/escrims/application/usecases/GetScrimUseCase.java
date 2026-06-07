package com.escrims.application.usecases;

import com.escrims.application.dto.ScrimResponseDTO;
import com.escrims.domain.repository.ScrimRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetScrimUseCase {

    private final ScrimRepository scrimRepository;

    public GetScrimUseCase(ScrimRepository scrimRepository) {
        this.scrimRepository = scrimRepository;
    }

    public ScrimResponseDTO execute(UUID scrimId) {
        return scrimRepository.findById(scrimId)
                .map(ScrimResponseDTO::from)
                .orElseThrow(() -> new IllegalArgumentException("Scrim no encontrado: " + scrimId));
    }
}
