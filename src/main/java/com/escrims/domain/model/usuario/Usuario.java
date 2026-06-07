package com.escrims.domain.model.usuario;

import com.escrims.domain.model.busqueda.BusquedaFavorita;
import com.escrims.domain.model.juego.JuegoFactory;
import com.escrims.domain.model.postulacion.Postulacion;
import com.escrims.domain.model.scrim.Confirmacion;
import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.valueobjects.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Usuario {

    private final UUID id;
    private String username;
    private String email;
    private String passwordHash;
    private boolean verificado;
    private RolSistema rol;
    private final List<PerfilJuego> perfilesJuego;
    private final List<DisponibilidadHoraria> disponibilidad;
    private PreferenciasUsuario preferencias;
    private final List<BusquedaFavorita> busquedasFavoritas;
    private int strikes;
    private LocalDateTime cooldownHasta;
    private final List<CredencialOAuth> credencialesOAuth;

    public Usuario(UUID id, String username, String email, String passwordHash) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.verificado = false;
        this.rol = new RolUser();
        this.perfilesJuego = new ArrayList<>();
        this.disponibilidad = new ArrayList<>();
        this.busquedasFavoritas = new ArrayList<>();
        this.credencialesOAuth = new ArrayList<>();
        this.strikes = 0;
    }

    public Postulacion postular(Scrim scrim, RolJuego rol) {
        return new Postulacion(this, scrim, rol);
    }

    public Confirmacion confirmar(Scrim scrim) {
        scrim.confirmarParticipacion(id);
        return scrim.getConfirmaciones().stream()
                .filter(c -> c.getUsuarioId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Confirmación no encontrada"));
    }

    public void reportarJugador(Long jugadorId) {
        // TODO(diagrama): ReporteConducta debería crearse aquí con scrim y motivo; falta en el diagrama.
    }

    public void verificarEmail() {
        this.verificado = true;
    }

    public void agregarStrike() {
        this.strikes++;
        if (strikes >= 3) {
            this.cooldownHasta = LocalDateTime.now().plusDays(7);
        }
    }

    public void aplicarCooldown(LocalDateTime hasta) {
        this.cooldownHasta = hasta;
    }

    public boolean estaBloqueado() {
        return cooldownHasta != null && LocalDateTime.now().isBefore(cooldownHasta);
    }

    public Rango getRangoPara(String juego) {
        return getRangoPorJuego().entrySet().stream()
                .filter(e -> e.getKey().equalsIgnoreCase(juego))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);
    }

    public void setRangoPara(String juego, Rango rango) {
        perfilesJuego.removeIf(p -> p.getJuego().getNombre().equalsIgnoreCase(juego));
        PerfilJuego perfil = new PerfilJuego(JuegoFactory.para(juego), getRegion(), rango.getNumerico());
        perfilesJuego.add(perfil);
    }

    public void actualizarRegion(Region region) {
        List<PerfilJuego> actualizados = new ArrayList<>();
        for (PerfilJuego perfil : perfilesJuego) {
            PerfilJuego copia = new PerfilJuego(perfil.getJuego(), region, perfil.getMmr());
            perfil.getRolesPreferidos().forEach(copia::agregarRolPreferido);
            actualizados.add(copia);
        }
        perfilesJuego.clear();
        perfilesJuego.addAll(actualizados);
    }

    public List<RolJuego> getRolesPreferidos() {
        return perfilesJuego.stream()
                .flatMap(p -> p.getRolesPreferidos().stream())
                .collect(Collectors.toList());
    }

    public Map<String, Rango> getRangoPorJuego() {
        Map<String, Rango> rangos = new HashMap<>();
        for (PerfilJuego perfil : perfilesJuego) {
            String nombre = perfil.getJuego().getNombre();
            int mmr = perfil.getMmr() != null ? perfil.getMmr() : 0;
            rangos.put(nombre, new Rango(nombre, "Perfil", mmr));
        }
        return Collections.unmodifiableMap(rangos);
    }

    public void agregarPerfilJuego(PerfilJuego perfil) {
        perfilesJuego.removeIf(p -> p.getJuego().getNombre().equalsIgnoreCase(perfil.getJuego().getNombre()));
        perfilesJuego.add(perfil);
    }

    public void agregarBusquedaFavorita(BusquedaFavorita busqueda) {
        busquedasFavoritas.add(busqueda);
    }

    public void agregarCredencialOAuth(CredencialOAuth credencial) {
        credencialesOAuth.removeIf(c -> c.getProveedor().getNombre().equals(credencial.getProveedor().getNombre()));
        credencialesOAuth.add(credencial);
    }

    public void actualizarPreferencias(PreferenciasUsuario preferencias) { this.preferencias = preferencias; }
    public void promoverA(RolSistema nuevoRol) { this.rol = nuevoRol; }

    public UUID getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public boolean isVerificado() { return verificado; }
    public RolSistema getRol() { return rol; }
    public Region getRegion() {
        return perfilesJuego.isEmpty() ? null : perfilesJuego.get(0).getRegion();
    }

    public PreferenciasUsuario getPreferencias() { return preferencias; }
    public int getStrikes() { return strikes; }
    public LocalDateTime getCooldownHasta() { return cooldownHasta; }
    public List<PerfilJuego> getPerfilesJuego() { return Collections.unmodifiableList(perfilesJuego); }
    public List<DisponibilidadHoraria> getDisponibilidad() { return Collections.unmodifiableList(disponibilidad); }
    public List<BusquedaFavorita> getBusquedasFavoritas() { return Collections.unmodifiableList(busquedasFavoritas); }
    public List<CredencialOAuth> getCredencialesOAuth() { return Collections.unmodifiableList(credencialesOAuth); }
}
