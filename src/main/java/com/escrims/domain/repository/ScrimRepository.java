package com.escrims.domain.repository;

import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.model.usuario.Usuario;
import com.escrims.domain.valueobjects.CriteriosBusqueda;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ScrimRepository {

    Optional<Scrim> findById(UUID id);

    Scrim save(Scrim scrim);

    List<Scrim> findByCriteria(CriteriosBusqueda criterios);

    List<Usuario> findCandidatosParaScrim(Scrim scrim);

    List<Scrim> findByEstado(String estadoNombre);

    void delete(UUID id);
}
