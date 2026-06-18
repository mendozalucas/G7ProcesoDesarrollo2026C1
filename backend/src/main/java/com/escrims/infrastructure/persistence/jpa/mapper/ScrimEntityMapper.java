package com.escrims.infrastructure.persistence.jpa.mapper;

import com.escrims.domain.model.juego.JuegoFactory;
import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.model.usuario.Jugador;
import com.escrims.domain.model.usuario.Usuario;
import com.escrims.domain.repository.UsuarioRepository;
import com.escrims.domain.state.ScrimStateFactory;
import com.escrims.domain.valueobjects.FormatoScrim;
import com.escrims.domain.valueobjects.Rango;
import com.escrims.domain.valueobjects.Region;
import com.escrims.infrastructure.persistence.jpa.entity.ScrimEntity;
import org.springframework.stereotype.Component;

@Component
public class ScrimEntityMapper {

    private final UsuarioRepository usuarioRepository;

    public ScrimEntityMapper(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Scrim toDomain(ScrimEntity entity) {
        Usuario organizadorUsuario = usuarioRepository.findById(entity.getOrganizadorId())
                .orElseGet(() -> new Jugador(entity.getOrganizadorId(), "organizador", "", ""));
        Jugador organizador = organizadorUsuario instanceof Jugador j
                ? j
                : new Jugador(organizadorUsuario.getId(), organizadorUsuario.getUsername(),
                organizadorUsuario.getEmail(), organizadorUsuario.getPasswordHash());

        String regionNombre = entity.getRegionServidor();
        if (entity.getRegionZona() != null && !entity.getRegionZona().isBlank()) {
            regionNombre = entity.getRegionServidor() + "/" + entity.getRegionZona();
        }

        FormatoScrim formato = new FormatoScrim(entity.getJugadoresPorLado() > 0 ? entity.getJugadoresPorLado() : 5);
        String modalidad = entity.getModalidad() != null ? entity.getModalidad() : "CASUAL";

        Scrim scrim = new Scrim(
                entity.getId(),
                JuegoFactory.para(entity.getJuego()),
                formato,
                modalidad,
                organizador,
                new Region(null, regionNombre),
                new Rango(null, entity.getRangoMinTier(), entity.getRangoMinNumerico()),
                new Rango(null, entity.getRangoMaxTier(), entity.getRangoMaxNumerico()),
                entity.getLatenciaMaxMs(),
                entity.getFechaHora());

        if (entity.getEstado() != null) {
            scrim.cambiarEstado(ScrimStateFactory.para(entity.getEstado()));
        }
        return scrim;
    }

    public ScrimEntity toEntity(Scrim scrim) {
        ScrimEntity entity = new ScrimEntity();
        entity.setId(scrim.getId());
        entity.setJuego(scrim.getJuego().getNombre());
        entity.setJugadoresPorLado(scrim.getFormato().getJugadoresPorLado());
        entity.setModalidad(scrim.getModalidad());

        String regionNombre = scrim.getRegion().getNombre();
        String servidor = regionNombre;
        String zona = "";
        int slash = regionNombre.indexOf('/');
        if (slash >= 0) {
            servidor = regionNombre.substring(0, slash);
            zona = regionNombre.substring(slash + 1);
        }
        entity.setRegionServidor(servidor);
        entity.setRegionZona(zona);
        entity.setRangoMinTier(scrim.getRangoMin().getNombre());
        entity.setRangoMinNumerico(scrim.getRangoMin().getMmr());
        entity.setRangoMaxTier(scrim.getRangoMax().getNombre());
        entity.setRangoMaxNumerico(scrim.getRangoMax().getMmr());
        entity.setLatenciaMaxMs(scrim.getLatenciaMax());
        entity.setFechaHora(scrim.getFechaHora());
        entity.setEstado(scrim.getEstadoNombre());
        entity.setMotivoCancelacion(scrim.getMotivoCancelacion());
        entity.setOrganizadorId(scrim.getOrganizador().getId());
        return entity;
    }
}
