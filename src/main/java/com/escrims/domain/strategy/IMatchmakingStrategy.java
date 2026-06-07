package com.escrims.domain.strategy;

import com.escrims.domain.model.usuario.Usuario;

import java.util.List;

public interface IMatchmakingStrategy {

    List<Usuario> seleccionar(List<Usuario> candidatos);
}
