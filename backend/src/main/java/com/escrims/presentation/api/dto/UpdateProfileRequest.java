package com.escrims.presentation.api.dto;

import java.util.List;

public class UpdateProfileRequest {

    private String username;
    private List<PerfilJuegoRequest> perfilesJuego;
    private List<DisponibilidadRequest> disponibilidad;
    private PreferenciasRequest preferencias;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public List<PerfilJuegoRequest> getPerfilesJuego() { return perfilesJuego; }
    public void setPerfilesJuego(List<PerfilJuegoRequest> perfilesJuego) { this.perfilesJuego = perfilesJuego; }
    public List<DisponibilidadRequest> getDisponibilidad() { return disponibilidad; }
    public void setDisponibilidad(List<DisponibilidadRequest> disponibilidad) { this.disponibilidad = disponibilidad; }
    public PreferenciasRequest getPreferencias() { return preferencias; }
    public void setPreferencias(PreferenciasRequest preferencias) { this.preferencias = preferencias; }

    public static class PerfilJuegoRequest {
        private String juego;
        private String servidor;
        private String zona;
        private Integer mmr;
        private List<String> rolesPreferidos;

        public String getJuego() { return juego; }
        public void setJuego(String juego) { this.juego = juego; }
        public String getServidor() { return servidor; }
        public void setServidor(String servidor) { this.servidor = servidor; }
        public String getZona() { return zona; }
        public void setZona(String zona) { this.zona = zona; }
        public Integer getMmr() { return mmr; }
        public void setMmr(Integer mmr) { this.mmr = mmr; }
        public List<String> getRolesPreferidos() { return rolesPreferidos; }
        public void setRolesPreferidos(List<String> rolesPreferidos) { this.rolesPreferidos = rolesPreferidos; }
    }

    public static class DisponibilidadRequest {
        private String dia;
        private String inicio;
        private String fin;

        public String getDia() { return dia; }
        public void setDia(String dia) { this.dia = dia; }
        public String getInicio() { return inicio; }
        public void setInicio(String inicio) { this.inicio = inicio; }
        public String getFin() { return fin; }
        public void setFin(String fin) { this.fin = fin; }
    }

    public static class PreferenciasRequest {
        private List<String> juegosPreferidos;
        private boolean recibirAlertas;

        public List<String> getJuegosPreferidos() { return juegosPreferidos; }
        public void setJuegosPreferidos(List<String> juegosPreferidos) { this.juegosPreferidos = juegosPreferidos; }
        public boolean isRecibirAlertas() { return recibirAlertas; }
        public void setRecibirAlertas(boolean recibirAlertas) { this.recibirAlertas = recibirAlertas; }
    }
}
