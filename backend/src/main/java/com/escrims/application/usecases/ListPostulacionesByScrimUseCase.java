package com.escrims.application.usecases;

import com.escrims.application.dto.PostulacionResponseDTO;
import com.escrims.domain.model.postulacion.Postulacion;
import com.escrims.domain.repository.PostulacionRepository;
import com.escrims.domain.repository.ScrimRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ListPostulacionesByScrimUseCase {

    private final PostulacionRepository postulacionRepository;
    private final ScrimRepository scrimRepository;

    public ListPostulacionesByScrimUseCase(PostulacionRepository postulacionRepository,
                                           ScrimRepository scrimRepository) {
        this.postulacionRepository = postulacionRepository;
        this.scrimRepository = scrimRepository;
    }

    public List<PostulacionResponseDTO> execute(UUID scrimId) {
        scrimRepository.findById(scrimId)
                .orElseThrow(() -> new IllegalArgumentException("Scrim no encontrado: " + scrimId));

        return postulacionRepository.findByScrimId(scrimId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private PostulacionResponseDTO toDto(Postulacion postulacion) {
        PostulacionResponseDTO dto = new PostulacionResponseDTO();
        dto.setId(postulacion.getId());
        dto.setUsuarioId(postulacion.getUsuario().getId().toString());
        dto.setUsername(postulacion.getUsuario().getUsername());
        dto.setRol(postulacion.getRolDeseado() != null ? postulacion.getRolDeseado().getNombre() : "");
        dto.setEstado(postulacion.getEstado().name());
        return dto;
    }
}
