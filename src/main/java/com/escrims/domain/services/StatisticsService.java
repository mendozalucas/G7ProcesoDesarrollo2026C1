package com.escrims.domain.services;

import com.escrims.domain.model.estadistica.Estadistica;
import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.model.usuario.Usuario;
import com.escrims.domain.repository.EstadisticaRepository;
import com.escrims.domain.repository.ScrimRepository;
import com.escrims.domain.repository.UsuarioRepository;
import com.escrims.domain.valueobjects.KDA;

import java.util.List;
import java.util.UUID;

public class StatisticsService {

    private final EstadisticaRepository estadisticaRepository;
    private final ScrimRepository scrimRepository;
    private final UsuarioRepository usuarioRepository;

    public StatisticsService(EstadisticaRepository estadisticaRepository,
                            ScrimRepository scrimRepository,
                            UsuarioRepository usuarioRepository) {
        this.estadisticaRepository = estadisticaRepository;
        this.scrimRepository = scrimRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public void cargarEstadistica(UUID scrimId, UUID usuarioId,
                                   boolean esMvp, KDA kda, String observaciones) {
        cargarEstadistica(
                scrimId,
                usuarioId,
                kda.getKills(),
                kda.getDeaths(),
                kda.getAssists(),
                esMvp,
                observaciones
        );
    }

    public void cargarEstadistica(UUID scrimId, UUID usuarioId,
                                   int kills, int deaths, int assists,
                                   boolean esMvp, String observaciones) {
        Scrim scrim = scrimRepository.findById(scrimId)
                .orElseThrow(() -> new IllegalArgumentException("Scrim no encontrado: " + scrimId));
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + usuarioId));

        if (!"FINALIZADO".equals(scrim.getEstadoNombre())) {
            throw new IllegalStateException("Solo se pueden cargar estadísticas de scrims finalizados");
        }

        Estadistica stat = new Estadistica(usuario, scrimId, kills, deaths, assists, esMvp, observaciones);
        estadisticaRepository.save(stat);
    }

    public double calcularRatingJugador(UUID usuarioId) {
        List<Estadistica> stats = estadisticaRepository.findByUsuarioId(usuarioId);
        if (stats.isEmpty()) return 0.0;
        return stats.stream()
                .mapToDouble(Estadistica::calcularKDA)
                .average()
                .orElse(0.0);
    }
}
