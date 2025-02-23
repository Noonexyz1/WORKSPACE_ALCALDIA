package com.prototipo.infrastructure.persistence.db.repository;

import com.prototipo.infrastructure.persistence.db.entity.CredencialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CredencialRepository extends JpaRepository<CredencialEntity, Long> {

    @Query(value =
            """
            SELECT *
            FROM credencial c
            WHERE c.fk_usuario_id = :idUser
            """, nativeQuery = true)
    CredencialEntity encontrarCredencialPorUsuarioId(@Param("idUser") Long idUser);

    @Query(value =
            """
            SELECT *
            FROM credencial c
            WHERE c.ci = :ci
            AND c.pass = :pass
            """, nativeQuery = true)
    CredencialEntity encontrarCredencial(@Param("ci") String ci, @Param("pass") String pass);
}

