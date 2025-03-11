package com.prototipo.infrastructure.persistence.db.repository;

import com.prototipo.infrastructure.persistence.db.entity.RolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<RolEntity, Long> {

    @Query(value =
            """
            SELECT *
            FROM rol r
            WHERE r.id = (
                SELECT uu.fk_rol_id
                FROM credencial c, usuario u, usuario_unidad uu
                WHERE c.fk_usuario_id = u.id
                AND uu.fk_usuario_id = u.id
                AND uu.is_active = TRUE
                AND c.ci = :ci
            )
            """, nativeQuery = true)
    RolEntity encontrarRolPorCi(@Param("ci") String ci);
}
