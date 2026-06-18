package com.escrims.infrastructure.persistence.jpa;

import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.model.usuario.Usuario;
import com.escrims.domain.valueobjects.CandidatoMatchmaking;
import com.escrims.domain.valueobjects.MatchmakingContext;
import com.escrims.infrastructure.persistence.jpa.entity.EstadisticaEntity;
import com.escrims.infrastructure.persistence.jpa.entity.PerfilEmbeddable;
import com.escrims.infrastructure.persistence.jpa.entity.PostulacionEntity;
import com.escrims.infrastructure.persistence.jpa.entity.ScrimEntity;
import com.escrims.infrastructure.persistence.jpa.entity.UsuarioEntity;
import com.escrims.infrastructure.persistence.jpa.mapper.UsuarioEntityMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
public class MatchmakingContextAssembler {

    private static final int CUPO_DEFAULT = 5;

    private final ScrimJpaRepository scrimJpaRepository;
    private final PostulacionJpaRepository postulacionJpaRepository;
    private final UsuarioJpaRepository usuarioJpaRepository;
    private final EstadisticaJpaRepository estadisticaJpaRepository;
    private final UsuarioEntityMapper usuarioMapper;

    public MatchmakingContextAssembler(ScrimJpaRepository scrimJpaRepository,
                                       PostulacionJpaRepository postulacionJpaRepository,
                                       UsuarioJpaRepository usuarioJpaRepository,
                                       EstadisticaJpaRepository estadisticaJpaRepository,
                                       UsuarioEntityMapper usuarioMapper) {
        this.scrimJpaRepository = scrimJpaRepository;
        this.postulacionJpaRepository = postulacionJpaRepository;
        this.usuarioJpaRepository = usuarioJpaRepository;
        this.estadisticaJpaRepository = estadisticaJpaRepository;
        this.usuarioMapper = usuarioMapper;
    }

    public MatchmakingContext build(Scrim scrim) {
        ScrimEntity entity = scrimJpaRepository.findById(scrim.getId())
                .orElseThrow(() -> new IllegalArgumentException("Scrim no encontrado: " + scrim.getId()));

        int cupo = cupoPara(entity);
        String juego = scrim.getJuego().getNombre();
        String regionScrim = scrim.getRegion().getNombre();

        List<CandidatoMatchmaking> candidatos = new ArrayList<>();
        for (PostulacionEntity postulacion : postulacionJpaRepository.findByScrimId(scrim.getId())) {
            if (!"ACEPTADA".equalsIgnoreCase(postulacion.getEstado())) {
                continue;
            }
            UsuarioEntity usuarioEntity = usuarioJpaRepository.findById(postulacion.getUsuarioId())
                    .orElseThrow(() -> new IllegalStateException(
                            "Usuario no encontrado: " + postulacion.getUsuarioId()));
            Usuario usuario = usuarioMapper.toDomain(usuarioEntity);
            candidatos.add(new CandidatoMatchmaking(
                    usuario,
                    resolverMmr(usuarioEntity, juego, scrim),
                    resolverPing(usuarioEntity, regionScrim),
                    postulacion.getRolNombre(),
                    usuarioEntity.getStrikes(),
                    kdaPromedio(usuarioEntity.getId())));
        }

        return new MatchmakingContext(scrim, cupo, candidatos);
    }

    private int cupoPara(ScrimEntity entity) {
        int porLado = entity.getJugadoresPorLado();
        if (porLado > 0) {
            return porLado * 2;
        }
        return CUPO_DEFAULT;
    }

    private int resolverMmr(UsuarioEntity usuario, String juego, Scrim scrim) {
        return usuario.getPerfiles().stream()
                .filter(p -> juego.equalsIgnoreCase(p.getJuego()))
                .map(PerfilEmbeddable::getMmr)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(estimateMmr(usuario.getId(), scrim));
    }

    private int estimateMmr(UUID usuarioId, Scrim scrim) {
        int centro = (scrim.getRangoMin().getMmr() + scrim.getRangoMax().getMmr()) / 2;
        int variacion = Math.abs(usuarioId.hashCode() % 201) - 100;
        return centro + variacion;
    }

    private int resolverPing(UsuarioEntity usuario, String regionScrim) {
        String servidorScrim = regionScrim;
        int slash = regionScrim.indexOf('/');
        if (slash >= 0) {
            servidorScrim = regionScrim.substring(0, slash);
        }

        String servidorUsuario = usuario.getPerfiles().isEmpty()
                ? ""
                : Objects.toString(usuario.getPerfiles().get(0).getServidor(), "");

        int base = servidorUsuario.equalsIgnoreCase(servidorScrim) ? 25 : 90;
        return base + Math.abs(usuario.getId().hashCode() % 35);
    }

    private double kdaPromedio(UUID usuarioId) {
        List<EstadisticaEntity> stats = estadisticaJpaRepository.findByUsuarioId(usuarioId);
        if (stats.isEmpty()) {
            return 1.0;
        }
        return stats.stream()
                .mapToDouble(this::calcularKda)
                .average()
                .orElse(1.0);
    }

    private double calcularKda(EstadisticaEntity stat) {
        if (stat.getDeaths() == 0) {
            return stat.getKills() + stat.getAssists();
        }
        return (stat.getKills() + stat.getAssists()) / (double) stat.getDeaths();
    }
}
