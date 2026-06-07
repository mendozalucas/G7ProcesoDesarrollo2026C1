package com.escrims.domain.services;

import com.escrims.domain.moderation.ModerationHandler;
import com.escrims.domain.model.reporte.ReporteConducta;
import com.escrims.domain.model.usuario.Usuario;
import com.escrims.domain.repository.UsuarioRepository;
import com.escrims.domain.strategy.sancion.SancionEstrategia;

import java.util.UUID;

public class ModerationService {

    private final ModerationHandler handlerChain;
    private final UsuarioRepository usuarioRepository;

    public ModerationService(ModerationHandler handlerChain, UsuarioRepository usuarioRepository) {
        this.handlerChain = handlerChain;
        this.usuarioRepository = usuarioRepository;
    }

    public void procesarReporte(ReporteConducta reporte) {
        handlerChain.handle(reporte);
        SancionEstrategia sancion = reporte.getSancion();
        if (sancion != null) {
            aplicarSancion(reporte.getReportadoId(), sancion);
        }
    }

    private void aplicarSancion(UUID usuarioId, SancionEstrategia sancion) {
        usuarioRepository.findById(usuarioId).ifPresent(u -> {
            sancion.aplicar(u);
            usuarioRepository.save(u);
        });
    }
}
