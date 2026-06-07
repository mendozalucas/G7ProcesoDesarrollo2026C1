package com.escrims.application.dto;

import com.escrims.domain.model.usuario.PerfilJuego;
import com.escrims.domain.model.usuario.Usuario;
import com.escrims.domain.valueobjects.DisponibilidadHoraria;
import com.escrims.domain.valueobjects.PreferenciasUsuario;
import com.escrims.domain.valueobjects.RolJuego;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class UsuarioProfileDTO {

    private UUID id;
    private String username;
    private String email;
    private boolean verificado;
    private String rol;
    private int strikes;
    private LocalDateTime cooldownHasta;
    private List<PerfilJuegoDTO> perfilesJuego;
    private List<DisponibilidadDTO> disponibilidad;
    private PreferenciasDTO preferencias;
    private List<String> proveedoresOAuth;

    public static UsuarioProfileDTO from(Usuario usuario) {
        UsuarioProfileDTO dto = new UsuarioProfileDTO();
        dto.id = usuario.getId();
        dto.username = usuario.getUsername();
        dto.email = usuario.getEmail();
        dto.verificado = usuario.isVerificado();
        dto.rol = usuario.getRol().getNombre();
        dto.strikes = usuario.getStrikes();
        dto.cooldownHasta = usuario.getCooldownHasta();
        dto.perfilesJuego = usuario.getPerfilesJuego().stream()
                .map(PerfilJuegoDTO::from)
                .collect(Collectors.toList());
        dto.disponibilidad = usuario.getDisponibilidad().stream()
                .map(DisponibilidadDTO::from)
                .collect(Collectors.toList());
        if (usuario.getPreferencias() != null) {
            dto.preferencias = PreferenciasDTO.from(usuario.getPreferencias());
        }
        dto.proveedoresOAuth = usuario.getCredencialesOAuth().stream()
                .map(c -> c.getProveedor().getNombre())
                .collect(Collectors.toList());
        return dto;
    }

    public UUID getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public boolean isVerificado() { return verificado; }
    public String getRol() { return rol; }
    public int getStrikes() { return strikes; }
    public LocalDateTime getCooldownHasta() { return cooldownHasta; }
    public List<PerfilJuegoDTO> getPerfilesJuego() { return perfilesJuego; }
    public List<DisponibilidadDTO> getDisponibilidad() { return disponibilidad; }
    public PreferenciasDTO getPreferencias() { return preferencias; }
    public List<String> getProveedoresOAuth() { return proveedoresOAuth; }

    public static class PerfilJuegoDTO {
        private String juego;
        private String servidor;
        private String zona;
        private Integer mmr;
        private List<String> rolesPreferidos;

        public static PerfilJuegoDTO from(PerfilJuego perfil) {
            PerfilJuegoDTO dto = new PerfilJuegoDTO();
            dto.juego = perfil.getJuego().getNombre();
            if (perfil.getRegion() != null) {
                dto.servidor = perfil.getRegion().getServidor();
                dto.zona = perfil.getRegion().getZona();
            }
            dto.mmr = perfil.getMmr();
            dto.rolesPreferidos = perfil.getRolesPreferidos().stream()
                    .map(RolJuego::getNombreRol)
                    .collect(Collectors.toList());
            return dto;
        }

        public String getJuego() { return juego; }
        public String getServidor() { return servidor; }
        public String getZona() { return zona; }
        public Integer getMmr() { return mmr; }
        public List<String> getRolesPreferidos() { return rolesPreferidos; }
    }

    public static class DisponibilidadDTO {
        private String dia;
        private String inicio;
        private String fin;

        public static DisponibilidadDTO from(DisponibilidadHoraria d) {
            DisponibilidadDTO dto = new DisponibilidadDTO();
            dto.dia = d.getDia().name();
            dto.inicio = d.getInicio().toString();
            dto.fin = d.getFin().toString();
            return dto;
        }

        public String getDia() { return dia; }
        public String getInicio() { return inicio; }
        public String getFin() { return fin; }
    }

    public static class PreferenciasDTO {
        private List<String> juegosPreferidos;
        private boolean recibirAlertas;

        public static PreferenciasDTO from(PreferenciasUsuario p) {
            PreferenciasDTO dto = new PreferenciasDTO();
            dto.juegosPreferidos = p.getJuegosPreferidos();
            dto.recibirAlertas = p.isRecibirAlertas();
            return dto;
        }

        public List<String> getJuegosPreferidos() { return juegosPreferidos; }
        public boolean isRecibirAlertas() { return recibirAlertas; }
    }
}
