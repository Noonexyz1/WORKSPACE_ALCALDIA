package com.prototipo.infrastructure.persistence.db.repository;

import com.prototipo.infrastructure.persistence.db.entity.Operacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperacionRepository extends JpaRepository<Operacion, Long> {

    @Query(value =
        """
        SELECT *
        FROM operacion o
        WHERE fk_solicitud_id IN (
            SELECT s.id
            FROM solicitud s
            WHERE fk_unidad_id IN (
                SELECT u.id
                FROM operador_unidad ou, unidad u
                WHERE ou.fk_unidad_id = u.id
                AND ou.fk_usuario_id = :idOperador
                AND ou.is_active = TRUE
            )
        )
        AND o.estado_by_operador = :estadoOpe
        """, nativeQuery = true)
    List<Operacion> findOperacionesByOperadorAndEstado(@Param("idOperador") Long idOperador,
                                                       @Param("estadoOpe") String estadoOpe);
}
