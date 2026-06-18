package com.escrims.domain.strategy;

import com.escrims.domain.model.usuario.Usuario;
import com.escrims.domain.valueobjects.MatchmakingContext;

import java.util.List;

public interface IMatchmakingStrategy {

    List<Usuario> seleccionar(MatchmakingContext context);
}
