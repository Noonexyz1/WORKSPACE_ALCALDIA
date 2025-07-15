package com.prototipo.infrastructure.persistence.db.repository;

import com.prototipo.infrastructure.persistence.db.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    @Query(value =
            """
            SELECT *
            FROM usuario u
            WHERE u.correo = :email
            """, nativeQuery = true)
    UsuarioEntity encontrarUsuarioPorEmail(@Param("email") String email);

    @Query(value =
            """
            SELECT *
            FROM usuario u
            WHERE u.ci = :ci
            """, nativeQuery = true)
    UsuarioEntity encontrarUsuarioPorCi(@Param("ci") String ci);

    UsuarioEntity findByCi(String ci);
}
