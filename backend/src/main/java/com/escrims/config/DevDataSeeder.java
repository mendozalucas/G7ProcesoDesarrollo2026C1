package com.escrims.config;

import com.escrims.application.dto.CreateScrimDTO;
import com.escrims.application.usecases.ApplyToScrimUseCase;
import com.escrims.application.usecases.CreateScrimUseCase;
import com.escrims.domain.model.usuario.Usuario;
import com.escrims.domain.repository.UsuarioRepository;
import com.escrims.domain.valueobjects.Rango;
import com.escrims.infrastructure.persistence.jpa.ScrimJpaRepository;
import com.escrims.infrastructure.persistence.jpa.UsuarioJpaRepository;
import com.escrims.infrastructure.persistence.jpa.entity.PerfilEmbeddable;
import com.escrims.infrastructure.security.PasswordHasher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Carga datos de demo al iniciar: un organizador, un scrim por juego y jugadores
 * (drivers) ya postulados para probar el flujo sin registrarlos a mano.
 */
@Component
@ConditionalOnProperty(name = "escrims.seed.enabled", havingValue = "true")
public class DevDataSeeder implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(DevDataSeeder.class);
    private static final String SEED_MARKER_EMAIL = "org@escrims.local";
    private static final String DEMO_PASSWORD = "secret123";

    private final UsuarioRepository usuarioRepository;
    private final CreateScrimUseCase createScrimUseCase;
    private final ApplyToScrimUseCase applyToScrimUseCase;
    private final ScrimJpaRepository scrimJpaRepository;
    private final UsuarioJpaRepository usuarioJpaRepository;

    public DevDataSeeder(UsuarioRepository usuarioRepository,
                         CreateScrimUseCase createScrimUseCase,
                         ApplyToScrimUseCase applyToScrimUseCase,
                         ScrimJpaRepository scrimJpaRepository,
                         UsuarioJpaRepository usuarioJpaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.createScrimUseCase = createScrimUseCase;
        this.applyToScrimUseCase = applyToScrimUseCase;
        this.scrimJpaRepository = scrimJpaRepository;
        this.usuarioJpaRepository = usuarioJpaRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (usuarioRepository.findByEmail(SEED_MARKER_EMAIL).isPresent()) {
            log.info("Datos de demo ya cargados ({}), se omite el seed.", SEED_MARKER_EMAIL);
            return;
        }

        String passwordHash = PasswordHasher.hash(DEMO_PASSWORD);
        Usuario organizador = guardarUsuario("organizador_demo", SEED_MARKER_EMAIL, passwordHash);

        seedJuego("valorant", "NA", "East", organizador.getId(), passwordHash, List.of(
                driver("driver_val_1", "driver.val1@escrims.local", "Duelist", 1400),
                driver("driver_val_2", "driver.val2@escrims.local", "Initiator", 1750),
                driver("driver_val_3", "driver.val3@escrims.local", "Sentinel", 2100)
        ));

        seedJuego("lol", "LAN", "AR", organizador.getId(), passwordHash, List.of(
                driver("driver_lol_1", "driver.lol1@escrims.local", "Top", 1550),
                driver("driver_lol_2", "driver.lol2@escrims.local", "Jungle", 1700),
                driver("driver_lol_3", "driver.lol3@escrims.local", "Mid", 1950)
        ));

        seedJuego("cs2", "EU", "West", organizador.getId(), passwordHash, List.of(
                driver("driver_cs2_1", "driver.cs1@escrims.local", "AWPer", 1480),
                driver("driver_cs2_2", "driver.cs2@escrims.local", "Entry", 1800),
                driver("driver_cs2_3", "driver.cs3@escrims.local", "Support", 2200)
        ));

        log.info("""
                Seed de demo cargado:
                  Organizador: {} / {} (pass: {})
                  3 scrims (valorant, lol, cs2) con 3 postulantes cada uno.
                  Iniciá sesión como organizador para ver la lista de postulantes.
                """, organizador.getUsername(), SEED_MARKER_EMAIL, DEMO_PASSWORD);
    }

    private void seedJuego(String juego,
                           String servidor,
                           String zona,
                           UUID organizadorId,
                           String passwordHash,
                           List<DriverSeed> drivers) {
        UUID scrimId = createScrimUseCase.execute(crearScrimDto(juego, servidor, zona, organizadorId));
        scrimJpaRepository.findById(scrimId).ifPresent(entity -> {
            entity.setJugadoresPorLado(2);
            scrimJpaRepository.save(entity);
        });
        for (DriverSeed driver : drivers) {
            Usuario usuario = guardarDriver(driver, juego, servidor, zona, passwordHash);
            applyToScrimUseCase.execute(usuario.getId(), scrimId, driver.rol());
        }
        log.info("Scrim {} creado ({}) con {} postulantes", scrimId, juego, drivers.size());
    }

    private static CreateScrimDTO crearScrimDto(String juego, String servidor, String zona, UUID organizadorId) {
        CreateScrimDTO dto = new CreateScrimDTO();
        dto.setJuego(juego);
        dto.setServidor(servidor);
        dto.setZona(zona);
        dto.setRangoMin(new Rango(null, "Gold", 1500));
        dto.setRangoMax(new Rango(null, "Plat", 2000));
        dto.setLatenciaMaxMs(80);
        dto.setFechaHora(LocalDateTime.now().plusDays(1).withHour(20).withMinute(0).withSecond(0).withNano(0));
        dto.setOrganizadorId(organizadorId);
        return dto;
    }

    private Usuario guardarUsuario(String username, String email, String passwordHash) {
        return usuarioRepository.save(new Usuario(UUID.randomUUID(), username, email, passwordHash));
    }

    private Usuario guardarDriver(DriverSeed driver, String juego, String servidor, String zona, String passwordHash) {
        Usuario usuario = guardarUsuario(driver.username(), driver.email(), passwordHash);
        usuarioJpaRepository.findById(usuario.getId()).ifPresent(entity -> {
            PerfilEmbeddable perfil = new PerfilEmbeddable();
            perfil.setJuego(juego);
            perfil.setServidor(servidor);
            perfil.setZona(zona);
            perfil.setMmr(driver.mmr());
            entity.getPerfiles().add(perfil);
            usuarioJpaRepository.save(entity);
        });
        return usuario;
    }

    private static DriverSeed driver(String username, String email, String rol, int mmr) {
        return new DriverSeed(username, email, rol, mmr);
    }

    private record DriverSeed(String username, String email, String rol, int mmr) {}
}
