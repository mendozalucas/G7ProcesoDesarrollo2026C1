package com.escrims.infrastructure.persistence.jpa.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
public class UsuarioEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    private boolean verificado;
    private String rol;
    private int strikes;
    private LocalDateTime cooldownHasta;

    private String juegosPreferidos;
    private boolean recibirAlertas;

    @ElementCollection
    @CollectionTable(name = "usuario_perfiles", joinColumns = @JoinColumn(name = "usuario_id"))
    private List<PerfilEmbeddable> perfiles = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "usuario_disponibilidad", joinColumns = @JoinColumn(name = "usuario_id"))
    private List<DisponibilidadEmbeddable> disponibilidad = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "usuario_oauth", joinColumns = @JoinColumn(name = "usuario_id"))
    private List<OAuthEmbeddable> credencialesOAuth = new ArrayList<>();

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public boolean isVerificado() { return verificado; }
    public void setVerificado(boolean verificado) { this.verificado = verificado; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    public int getStrikes() { return strikes; }
    public void setStrikes(int strikes) { this.strikes = strikes; }
    public LocalDateTime getCooldownHasta() { return cooldownHasta; }
    public void setCooldownHasta(LocalDateTime cooldownHasta) { this.cooldownHasta = cooldownHasta; }
    public String getJuegosPreferidos() { return juegosPreferidos; }
    public void setJuegosPreferidos(String juegosPreferidos) { this.juegosPreferidos = juegosPreferidos; }
    public boolean isRecibirAlertas() { return recibirAlertas; }
    public void setRecibirAlertas(boolean recibirAlertas) { this.recibirAlertas = recibirAlertas; }
    public List<PerfilEmbeddable> getPerfiles() { return perfiles; }
    public void setPerfiles(List<PerfilEmbeddable> perfiles) { this.perfiles = perfiles; }
    public List<DisponibilidadEmbeddable> getDisponibilidad() { return disponibilidad; }
    public void setDisponibilidad(List<DisponibilidadEmbeddable> disponibilidad) { this.disponibilidad = disponibilidad; }
    public List<OAuthEmbeddable> getCredencialesOAuth() { return credencialesOAuth; }
    public void setCredencialesOAuth(List<OAuthEmbeddable> credencialesOAuth) { this.credencialesOAuth = credencialesOAuth; }
}
