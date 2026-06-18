package com.escrims.infrastructure.persistence.jpa.mapper;

import com.escrims.domain.model.postulacion.EstadoPostulacion;
import com.escrims.domain.model.postulacion.Postulacion;
import com.escrims.domain.model.rol.Rol;
import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.model.usuario.Usuario;
import com.escrims.domain.repository.ScrimRepository;
import com.escrims.domain.repository.UsuarioRepository;
import com.escrims.infrastructure.persistence.jpa.entity.PostulacionEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PostulacionEntityMapper {

    private final UsuarioRepository usuarioRepository;
    private final ScrimRepository scrimRepository;

    public PostulacionEntityMapper(UsuarioRepository usuarioRepository, ScrimRepository scrimRepository) {
        this.usuarioRepository = usuarioRepository;
        this.scrimRepository = scrimRepository;
    }

    public Postulacion toDomain(PostulacionEntity entity) {
        Usuario usuario = usuarioRepository.findById(entity.getUsuarioId())
                .orElseGet(() -> new Usuario(entity.getUsuarioId(), "usuario", "", ""));
        Scrim scrim = scrimRepository.findById(entity.getScrimId())
                .orElseThrow(() -> new IllegalStateException("Scrim no encontrado: " + entity.getScrimId()));
        Rol rol = entity.getRolNombre() != null ? new Rol(null, entity.getRolNombre()) : null;
        EstadoPostulacion estado = entity.getEstado() != null
                ? EstadoPostulacion.valueOf(entity.getEstado().toUpperCase())
                : EstadoPostulacion.PENDIENTE;

        return new Postulacion(entity.getId(), usuario, scrim, rol, estado);
    }

    public PostulacionEntity toEntity(Postulacion postulacion) {
        PostulacionEntity entity = new PostulacionEntity();
        entity.setId(postulacion.getId());
        entity.setUsuarioId(postulacion.getUsuario().getId());
        entity.setScrimId(postulacion.getScrim().getId());
        if (postulacion.getRolDeseado() != null) {
            entity.setRolJuego(postulacion.getScrim().getJuego().getNombre());
            entity.setRolNombre(postulacion.getRolDeseado().getNombre());
        }
        entity.setEstado(postulacion.getEstado().name());
        return entity;
    }
}
