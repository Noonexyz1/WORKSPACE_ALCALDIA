package com.prototipo.infrastructure.persistence.db.repository;

import com.prototipo.infrastructure.persistence.db.entity.UsuarioUnidadEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioUnidadRepository extends JpaRepository<UsuarioUnidadEntity, Long> {

    @Query(value =
            """
            SELECT uu.id, u.nombres, u.paterno, u.materno, u.ci, u.correo, r.nombre_rol, u2.nombre_unidad, c.nombre_cargo
            FROM usuario_unidad uu, rol r, usuario u, cargo c, unidad u2, credencial c2
            WHERE u.id = uu.fk_usuario_id
            AND u2.id = uu.fk_unidad_id
            AND r.id = uu.fk_rol_id
            AND c.id = uu.fk_cargo_id
            AND u.id = c2.fk_usuario_id
            AND c2.ci = :ci
            AND c2.pass = :pass
            LIMIT 1
            """, nativeQuery = true)
    Object[] findUsuarioByCredencial(
            @Param("ci") String correo,
            @Param("pass") String pass
    );

    @Query(value =
            """
            SELECT *
            FROM usuario_unidad uu
            WHERE uu.fk_usuario_id = :idUsuario
            AND uu.is_active = TRUE
            """, nativeQuery = true)
    UsuarioUnidadEntity findUsuariosUnidadPorUsuarioId(
            @Param("idUsuario") Long idUsuario);

    @Query(value =
            """
            SELECT *
            FROM usuario_unidad uu
            WHERE uu.is_active = TRUE
            """, nativeQuery = true)
    Page<UsuarioUnidadEntity> getListaUsuarioUnidad(Pageable pageable);

    @Query(value =
            """
            SELECT *
            FROM usuario_unidad uu
            WHERE uu.fk_usuario_id = (
                SELECT u.id
                FROM usuario u
                WHERE u.ci = :ci)
            AND 0 = ALL (
                    SELECT uu.is_active
                    FROM usuario_unidad uu
                    WHERE uu.fk_usuario_id = (
                        SELECT u.id
                        FROM usuario u
                        WHERE u.ci = :ci))
            LIMIT 1
            """, nativeQuery = true)
    UsuarioUnidadEntity findUserUnidadByCi(@Param("ci") String ci);
}
