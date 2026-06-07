package com.escrims.infrastructure.persistence.jpa.mapper;

import com.escrims.domain.model.juego.JuegoFactory;
import com.escrims.domain.model.scrim.Confirmacion;
import com.escrims.domain.model.scrim.Equipo;
import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.valueobjects.FormatoScrim;
import com.escrims.domain.valueobjects.LadoEquipo;
import com.escrims.domain.valueobjects.Latencia;
import com.escrims.domain.valueobjects.ModalidadFactory;
import com.escrims.domain.valueobjects.Rango;
import com.escrims.domain.valueobjects.RangosPermitidos;
import com.escrims.domain.valueobjects.Region;
import com.escrims.domain.valueobjects.RolJuego;
import com.escrims.infrastructure.persistence.jpa.entity.ConfirmacionEmbeddable;
import com.escrims.infrastructure.persistence.jpa.entity.ParticipanteEmbeddable;
import com.escrims.infrastructure.persistence.jpa.entity.ReglaRolEmbeddable;
import com.escrims.infrastructure.persistence.jpa.entity.ScrimEntity;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ScrimEntityMapper {

    public Scrim toDomain(ScrimEntity entity) {
        RangosPermitidos rangos = new RangosPermitidos(
                new Rango(entity.getRangoMinJuego(), entity.getRangoMinTier(), entity.getRangoMinNumerico()),
                new Rango(entity.getRangoMaxJuego(), entity.getRangoMaxTier(), entity.getRangoMaxNumerico()));

        Map<RolJuego, Integer> reglasRoles = new HashMap<>();
        for (ReglaRolEmbeddable regla : entity.getReglasRoles()) {
            reglasRoles.put(new RolJuego(regla.getRolJuego(), regla.getRolNombre()), regla.getCantidad());
        }

        List<Equipo> equipos = construirEquipos(entity);
        List<Confirmacion> confirmaciones = entity.getConfirmaciones().stream()
                .map(c -> Confirmacion.reconstituir(c.getId(), c.getUsuarioId(),
                        c.isConfirmado(), c.getFechaConfirmacion()))
                .collect(Collectors.toList());

        return Scrim.reconstituir(
                entity.getId(),
                JuegoFactory.para(entity.getJuego()),
                new FormatoScrim(entity.getJugadoresPorLado()),
                new Region(entity.getRegionServidor(), entity.getRegionZona()),
                rangos,
                new Latencia(entity.getLatenciaMaxMs()),
                entity.getFechaHora(),
                Duration.ofMinutes(entity.getDuracionMinutos()),
                ModalidadFactory.para(entity.getModalidad()),
                reglasRoles,
                entity.getOrganizadorId(),
                entity.getEstado(),
                entity.getMotivoCancelacion(),
                equipos,
                confirmaciones);
    }

    public ScrimEntity toEntity(Scrim scrim) {
        ScrimEntity entity = new ScrimEntity();
        entity.setId(scrim.getId());
        entity.setJuego(scrim.getNombreJuego());
        entity.setJugadoresPorLado(scrim.getFormato().getJugadoresPorLado());
        entity.setRegionServidor(scrim.getRegion().getServidor());
        entity.setRegionZona(scrim.getRegion().getZona());
        entity.setRangoMinJuego(scrim.getRangosPermitidos().getMin().getJuego());
        entity.setRangoMinTier(scrim.getRangosPermitidos().getMin().getTier());
        entity.setRangoMinNumerico(scrim.getRangosPermitidos().getMin().getNumerico());
        entity.setRangoMaxJuego(scrim.getRangosPermitidos().getMax().getJuego());
        entity.setRangoMaxTier(scrim.getRangosPermitidos().getMax().getTier());
        entity.setRangoMaxNumerico(scrim.getRangosPermitidos().getMax().getNumerico());
        entity.setLatenciaMaxMs(scrim.getLatenciaMax().getPingMs());
        entity.setFechaHora(scrim.getFechaHora());
        entity.setDuracionMinutos(scrim.getDuracionEstimada().toMinutes());
        entity.setModalidad(scrim.getModalidad().getNombre());
        entity.setEstado(scrim.getEstadoNombre());
        entity.setMotivoCancelacion(scrim.getMotivoCancelacion());
        entity.setOrganizadorId(scrim.getOrganizadorId());

        List<ReglaRolEmbeddable> reglas = scrim.getReglasRoles().entrySet().stream()
                .map(e -> {
                    ReglaRolEmbeddable regla = new ReglaRolEmbeddable();
                    regla.setRolJuego(e.getKey().getJuego());
                    regla.setRolNombre(e.getKey().getNombreRol());
                    regla.setCantidad(e.getValue());
                    return regla;
                })
                .collect(Collectors.toList());
        entity.setReglasRoles(reglas);

        List<ParticipanteEmbeddable> participantes = new ArrayList<>();
        for (Equipo equipo : scrim.getEquipos()) {
            if (LadoEquipo.EQUIPO_A.equals(equipo.getLado())) {
                entity.setCapitanEquipoA(equipo.getCapitanId());
            } else if (LadoEquipo.EQUIPO_B.equals(equipo.getLado())) {
                entity.setCapitanEquipoB(equipo.getCapitanId());
            }
            for (var participante : equipo.getParticipantes()) {
                ParticipanteEmbeddable emb = new ParticipanteEmbeddable();
                emb.setLado(equipo.getLado().getNombre());
                emb.setUsuarioId(participante.getUsuarioId());
                RolJuego rol = participante.getRolAsignado();
                if (rol != null) {
                    emb.setRolJuego(rol.getJuego());
                    emb.setRolNombre(rol.getNombreRol());
                }
                participantes.add(emb);
            }
        }
        entity.setParticipantes(participantes);

        List<ConfirmacionEmbeddable> confirmaciones = scrim.getConfirmaciones().stream()
                .map(c -> {
                    ConfirmacionEmbeddable emb = new ConfirmacionEmbeddable();
                    emb.setId(c.getId());
                    emb.setUsuarioId(c.getUsuarioId());
                    emb.setConfirmado(c.isConfirmado());
                    emb.setFechaConfirmacion(c.getFechaConfirmacion());
                    return emb;
                })
                .collect(Collectors.toList());
        entity.setConfirmaciones(confirmaciones);

        return entity;
    }

    private List<Equipo> construirEquipos(ScrimEntity entity) {
        Equipo equipoA = new Equipo(LadoEquipo.EQUIPO_A);
        Equipo equipoB = new Equipo(LadoEquipo.EQUIPO_B);
        if (entity.getCapitanEquipoA() != null) {
            equipoA.setCapitanId(entity.getCapitanEquipoA());
        }
        if (entity.getCapitanEquipoB() != null) {
            equipoB.setCapitanId(entity.getCapitanEquipoB());
        }

        for (ParticipanteEmbeddable p : entity.getParticipantes()) {
            RolJuego rol = p.getRolJuego() != null
                    ? new RolJuego(p.getRolJuego(), p.getRolNombre())
                    : null;
            if (LadoEquipo.EQUIPO_A.getNombre().equalsIgnoreCase(p.getLado())) {
                equipoA.agregarParticipante(p.getUsuarioId(), rol);
            } else {
                equipoB.agregarParticipante(p.getUsuarioId(), rol);
            }
        }
        return List.of(equipoA, equipoB);
    }
}
