package com.escrims.infrastructure.persistence.jpa;

import com.escrims.infrastructure.persistence.jpa.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioJpaRepository extends JpaRepository<UsuarioEntity, UUID> {

    Optional<UsuarioEntity> findByEmailIgnoreCase(String email);

    Optional<UsuarioEntity> findByUsernameIgnoreCase(String username);

    @Query("SELECT u FROM UsuarioEntity u JOIN u.credencialesOAuth c "
            + "WHERE UPPER(c.proveedor) = UPPER(:proveedor) AND c.externalId = :externalId")
    Optional<UsuarioEntity> findByOAuthCredencial(@Param("proveedor") String proveedor,
                                                   @Param("externalId") String externalId);
}
