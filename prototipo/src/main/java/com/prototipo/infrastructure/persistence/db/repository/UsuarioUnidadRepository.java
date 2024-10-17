package com.prototipo.infrastructure.persistence.db.repository;

import com.prototipo.infrastructure.persistence.db.entity.UsuarioUnidadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioUnidadRepository extends JpaRepository<UsuarioUnidadEntity, Long> {

    @Query(value =
            """
            SELECT *
            FROM usuario_unidad uu
            WHERE uu.fk_usuario_id = (
                SELECT u.id
                FROM usuario u
                WHERE u.id = (
                    SELECT c.fk_usuario_id
                    FROM credencial c
                    WHERE c.correo = :correo
                    AND c.pass = :pass
                )
            )
            AND uu.is_active = TRUE
            LIMIT 1
            """, nativeQuery = true)
    UsuarioUnidadEntity findUsuarioUnidadByCredencial(@Param("correo") String correo,
                                                @Param("pass") String pass);

    @Query(value =
            """
            SELECT *
            FROM usuario_unidad uu
            WHERE uu.fk_usuario_id = :idUsuario;
            """, nativeQuery = true)
    UsuarioUnidadEntity encontrarUsuarioUnidadPorUsuarioId(@Param("idUsuario") Long idUsuario);
}
