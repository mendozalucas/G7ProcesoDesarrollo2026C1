package com.escrims.infrastructure.persistence.jpa.mapper;

import com.escrims.domain.model.juego.JuegoFactory;
import com.escrims.domain.model.lobby.GestorLobby;
import com.escrims.domain.model.rol.Rol;
import com.escrims.domain.model.scrim.Confirmacion;
import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.model.usuario.Jugador;
import com.escrims.domain.model.usuario.Usuario;
import com.escrims.domain.repository.UsuarioRepository;
import com.escrims.domain.state.ScrimStateFactory;
import com.escrims.domain.valueobjects.FormatoScrim;
import com.escrims.domain.valueobjects.Rango;
import com.escrims.domain.valueobjects.Region;
import com.escrims.infrastructure.persistence.jpa.entity.ConfirmacionEmbeddable;
import com.escrims.infrastructure.persistence.jpa.entity.ParticipanteEmbeddable;
import com.escrims.infrastructure.persistence.jpa.entity.ScrimEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

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

        restaurarLobby(scrim, entity);
        return scrim;
    }

    public ScrimEntity toEntity(Scrim scrim) {
        ScrimEntity entity = new ScrimEntity();
        entity.setId(scrim.getId());
        populateEntity(entity, scrim);
        return entity;
    }

    public void populateEntity(ScrimEntity entity, Scrim scrim) {
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

        entity.setParticipantes(mapParticipantes(scrim));
        entity.setConfirmaciones(mapConfirmaciones(scrim));
    }

    private void restaurarLobby(Scrim scrim, ScrimEntity entity) {
        GestorLobby gestor = scrim.getLobby().getGestorLobby();

        for (ParticipanteEmbeddable p : entity.getParticipantes()) {
            Jugador jugador = cargarJugador(p.getUsuarioId());
            if ("A".equalsIgnoreCase(p.getLado())) {
                gestor.getEquipoA().agregarJugador(jugador);
            } else {
                gestor.getEquipoB().agregarJugador(jugador);
            }
            if (p.getRolNombre() != null && !p.getRolNombre().isBlank()) {
                gestor.asignarRol(jugador, new Rol(null, p.getRolNombre()));
            }
        }

        for (ConfirmacionEmbeddable c : entity.getConfirmaciones()) {
            Jugador jugador = cargarJugador(c.getUsuarioId());
            Confirmacion confirmacion = scrim.getLobby().agregarConfirmacion(jugador, scrim);
            if (c.isConfirmado()) {
                confirmacion.confirmar();
            }
        }
    }

    private ArrayList<ParticipanteEmbeddable> mapParticipantes(Scrim scrim) {
        ArrayList<ParticipanteEmbeddable> participantes = new ArrayList<>();
        GestorLobby gestor = scrim.getLobby().getGestorLobby();
        String juego = scrim.getJuego().getNombre();

        gestor.getEquipoA().getJugadores().forEach(j ->
                participantes.add(toParticipanteEmb("A", j, gestor.getRolDe(j), juego)));
        gestor.getEquipoB().getJugadores().forEach(j ->
                participantes.add(toParticipanteEmb("B", j, gestor.getRolDe(j), juego)));
        return participantes;
    }

    private ParticipanteEmbeddable toParticipanteEmb(String lado, Jugador jugador, Rol rol, String juego) {
        ParticipanteEmbeddable p = new ParticipanteEmbeddable();
        p.setLado(lado);
        p.setUsuarioId(jugador.getId());
        p.setRolJuego(juego);
        if (rol != null) {
            p.setRolNombre(rol.getNombre());
        }
        return p;
    }

    private ArrayList<ConfirmacionEmbeddable> mapConfirmaciones(Scrim scrim) {
        ArrayList<ConfirmacionEmbeddable> confirmaciones = new ArrayList<>();
        for (Confirmacion c : scrim.getLobby().getConfirmaciones()) {
            ConfirmacionEmbeddable emb = new ConfirmacionEmbeddable();
            emb.setUsuarioId(c.getJugador().getId());
            emb.setConfirmado(c.isConfirmado());
            if (c.isConfirmado()) {
                emb.setFechaConfirmacion(LocalDateTime.now());
            }
            confirmaciones.add(emb);
        }
        return confirmaciones;
    }

    private Jugador cargarJugador(UUID usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseGet(() -> new Jugador(usuarioId, "jugador", "", ""));
        if (usuario instanceof Jugador jugador) {
            return jugador;
        }
        return new Jugador(usuario.getId(), usuario.getUsername(), usuario.getEmail(), usuario.getPasswordHash());
    }
}
