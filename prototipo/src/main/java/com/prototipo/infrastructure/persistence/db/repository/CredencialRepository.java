package com.prototipo.infrastructure.persistence.db.repository;

import com.prototipo.infrastructure.persistence.db.entity.CredencialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CredencialRepository extends JpaRepository<CredencialEntity, Long> {
/*
//    @Query(value = "SELECT * FROM credencial c WHERE c.correo = :correo AND c.pass = :password", nativeQuery = true)
    Optional<CredencialEntity> findByUsernameAndPassword(@Param("correo") String correo,
                                                         @Param("password") String password);*/

    @Query(value =
            """
            SELECT *
            FROM credencial c
            WHERE c.fk_usuario_id = :idUser
            """, nativeQuery = true)
    CredencialEntity encontrarCredencialPorUsuarioId(@Param("idUser") Long idUser);

}

