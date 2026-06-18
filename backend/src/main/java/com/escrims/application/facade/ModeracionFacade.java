package com.escrims.application.facade;

import com.escrims.domain.facade.ModeracionFacadePort;
import com.escrims.domain.model.rating.Rating;
import com.escrims.domain.model.reporte.ReporteConducta;
import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.model.usuario.Jugador;
import com.escrims.domain.repository.ReporteConductaRepository;
import com.escrims.domain.services.ModerationService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ModeracionFacade implements ModeracionFacadePort {

    private final ModerationService moderationService;
    private final ReporteConductaRepository reporteRepository;
    private final Map<Long, Rating> ratings = new ConcurrentHashMap<>();
    private final AtomicLong ratingSeq = new AtomicLong(1);

    public ModeracionFacade(ModerationService moderationService,
                            ReporteConductaRepository reporteRepository) {
        this.moderationService = moderationService;
        this.reporteRepository = reporteRepository;
    }

    @Override
    public ReporteConducta reportarJugador(Jugador jugador, Jugador reportado, String motivo) {
        String motivoCompleto = String.format("[%s reporta a %s] %s",
                jugador.getUsername(), reportado.getUsername(), motivo);
        ReporteConducta reporte = new ReporteConducta(null, motivoCompleto);
        ReporteConducta guardado = reporteRepository.save(reporte);
        moderationService.procesarReporte(guardado);
        reporteRepository.save(guardado);
        return guardado;
    }

    @Override
    public Rating calificarJugador(Jugador califica, Jugador calificado, Scrim scrim,
                                   int puntuacion, String comentario) {
        if (puntuacion < 1 || puntuacion > 5) {
            throw new IllegalArgumentException("La puntuación debe estar entre 1 y 5");
        }
        long id = ratingSeq.getAndIncrement();
        Rating rating = new Rating(id, califica, calificado, scrim, puntuacion, comentario);
        ratings.put(id, rating);
        return rating;
    }
}
