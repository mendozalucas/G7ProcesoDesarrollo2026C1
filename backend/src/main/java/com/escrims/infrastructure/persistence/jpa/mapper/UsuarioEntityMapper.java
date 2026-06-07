package com.escrims.infrastructure.persistence.jpa.mapper;

import com.escrims.domain.model.juego.JuegoFactory;
import com.escrims.domain.model.usuario.PerfilJuego;
import com.escrims.domain.model.usuario.RolSistemaFactory;
import com.escrims.domain.model.usuario.Usuario;
import com.escrims.domain.valueobjects.CredencialOAuth;
import com.escrims.domain.valueobjects.DisponibilidadHoraria;
import com.escrims.domain.valueobjects.OAuthProveedorFactory;
import com.escrims.domain.valueobjects.PreferenciasUsuario;
import com.escrims.domain.valueobjects.Region;
import com.escrims.domain.valueobjects.RolJuego;
import com.escrims.infrastructure.persistence.jpa.entity.DisponibilidadEmbeddable;
import com.escrims.infrastructure.persistence.jpa.entity.OAuthEmbeddable;
import com.escrims.infrastructure.persistence.jpa.entity.PerfilEmbeddable;
import com.escrims.infrastructure.persistence.jpa.entity.UsuarioEntity;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioEntityMapper {

    public Usuario toDomain(UsuarioEntity entity) {
        Usuario usuario = new Usuario(entity.getId(), entity.getUsername(),
                entity.getEmail(), entity.getPasswordHash());
        usuario.restaurarEstadoPersistido(entity.isVerificado(),
                RolSistemaFactory.para(entity.getRol()),
                entity.getStrikes(), entity.getCooldownHasta());

        for (PerfilEmbeddable perfilEmb : entity.getPerfiles()) {
            PerfilJuego perfil = new PerfilJuego(
                    JuegoFactory.para(perfilEmb.getJuego()),
                    new Region(perfilEmb.getServidor(), perfilEmb.getZona()),
                    perfilEmb.getMmr());
            parseRolesPreferidos(perfilEmb.getRolesPreferidos())
                    .forEach(perfil::agregarRolPreferido);
            usuario.agregarPerfilJuego(perfil);
        }

        List<DisponibilidadHoraria> horarios = entity.getDisponibilidad().stream()
                .map(d -> new DisponibilidadHoraria(
                        DayOfWeek.valueOf(d.getDia()),
                        LocalTime.parse(d.getInicio()),
                        LocalTime.parse(d.getFin())))
                .collect(Collectors.toList());
        usuario.reemplazarDisponibilidad(horarios);

        for (OAuthEmbeddable oauth : entity.getCredencialesOAuth()) {
            usuario.agregarCredencialOAuth(new CredencialOAuth(
                    OAuthProveedorFactory.para(oauth.getProveedor()),
                    oauth.getExternalId()));
        }

        if (entity.getJuegosPreferidos() != null || entity.isRecibirAlertas()) {
            List<String> juegos = entity.getJuegosPreferidos() == null || entity.getJuegosPreferidos().isBlank()
                    ? Collections.emptyList()
                    : Arrays.asList(entity.getJuegosPreferidos().split(","));
            usuario.actualizarPreferencias(new PreferenciasUsuario(juegos, entity.isRecibirAlertas()));
        }

        return usuario;
    }

    public UsuarioEntity toEntity(Usuario usuario) {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setId(usuario.getId());
        entity.setUsername(usuario.getUsername());
        entity.setEmail(usuario.getEmail());
        entity.setPasswordHash(usuario.getPasswordHash());
        entity.setVerificado(usuario.isVerificado());
        entity.setRol(RolSistemaFactory.nombre(usuario.getRol()));
        entity.setStrikes(usuario.getStrikes());
        entity.setCooldownHasta(usuario.getCooldownHasta());

        List<PerfilEmbeddable> perfiles = usuario.getPerfilesJuego().stream()
                .map(p -> {
                    PerfilEmbeddable emb = new PerfilEmbeddable();
                    emb.setJuego(p.getJuego().getNombre());
                    Region region = p.getRegion();
                    if (region != null) {
                        emb.setServidor(region.getServidor());
                        emb.setZona(region.getZona());
                    }
                    emb.setMmr(p.getMmr());
                    emb.setRolesPreferidos(formatRolesPreferidos(p.getRolesPreferidos()));
                    return emb;
                })
                .collect(Collectors.toList());
        entity.setPerfiles(perfiles);

        List<DisponibilidadEmbeddable> disponibilidad = usuario.getDisponibilidad().stream()
                .map(d -> {
                    DisponibilidadEmbeddable emb = new DisponibilidadEmbeddable();
                    emb.setDia(d.getDia().name());
                    emb.setInicio(d.getInicio().toString());
                    emb.setFin(d.getFin().toString());
                    return emb;
                })
                .collect(Collectors.toList());
        entity.setDisponibilidad(disponibilidad);

        List<OAuthEmbeddable> oauth = usuario.getCredencialesOAuth().stream()
                .map(c -> {
                    OAuthEmbeddable emb = new OAuthEmbeddable();
                    emb.setProveedor(c.getProveedor().getNombre().toUpperCase());
                    emb.setExternalId(c.getExternalId());
                    return emb;
                })
                .collect(Collectors.toList());
        entity.setCredencialesOAuth(oauth);

        PreferenciasUsuario pref = usuario.getPreferencias();
        if (pref != null) {
            entity.setJuegosPreferidos(String.join(",", pref.getJuegosPreferidos()));
            entity.setRecibirAlertas(pref.isRecibirAlertas());
        }

        return entity;
    }

    private List<RolJuego> parseRolesPreferidos(String raw) {
        if (raw == null || raw.isBlank()) {
            return Collections.emptyList();
        }
        List<RolJuego> roles = new ArrayList<>();
        for (String token : raw.split(",")) {
            String[] parts = token.split(":", 2);
            if (parts.length == 2) {
                roles.add(new RolJuego(parts[0], parts[1]));
            }
        }
        return roles;
    }

    private String formatRolesPreferidos(List<RolJuego> roles) {
        return roles.stream()
                .map(r -> r.getJuego() + ":" + r.getNombreRol())
                .collect(Collectors.joining(","));
    }
}
