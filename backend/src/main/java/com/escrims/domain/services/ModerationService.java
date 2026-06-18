package com.escrims.domain.services;

import com.escrims.domain.moderation.ModerationHandler;
import com.escrims.domain.model.reporte.ReporteConducta;
import com.escrims.domain.repository.UsuarioRepository;

public class ModerationService {

    private final ModerationHandler handlerChain;
    private final UsuarioRepository usuarioRepository;

    public ModerationService(ModerationHandler handlerChain, UsuarioRepository usuarioRepository) {
        this.handlerChain = handlerChain;
        this.usuarioRepository = usuarioRepository;
    }

    public void procesarReporte(ReporteConducta reporte) {
        handlerChain.handle(reporte);
    }
}
