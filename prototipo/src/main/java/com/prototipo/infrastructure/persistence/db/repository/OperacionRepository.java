package com.prototipo.infrastructure.persistence.db.repository;

import com.prototipo.infrastructure.persistence.db.entity.Operacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OperacionRepository extends JpaRepository<Operacion, Long> {

    //TODO, estas consultas estan mal
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
            AND o.estado_by_operador = "Pendiente"
            """, nativeQuery = true)
    Page<Operacion> findOperacionesByOperadorAndEstadoPendiente(@Param("idOperador") Long idOperador,
                                                                Pageable pageable);

    //TODO, estas consultas estan mal
    @Query(value =
            """
            SELECT *
            FROM operacion o
            WHERE o.fk_operador_id = :idOperador
            AND o.estado_by_operador = 'Iniciado'
            AND o.fk_solicitud_id NOT IN (
                SELECT o2.fk_solicitud_id
                FROM operacion o2
                WHERE o2.fk_operador_id = :idOperador
                AND o2.estado_by_operador = 'Completado'
            )
            """, nativeQuery = true)
    Page<Operacion> findOperacionesByOperadorAndEstadoIniciado(@Param("idOperador") Long idOperador,
                                                                Pageable pageable);

    //TODO, estas consultas estan mal
    @Query(value =
            """
            SELECT *
            FROM operacion o
            WHERE o.fk_operador_id = :idOperador
            AND o.estado_by_operador = "Completado"
            """, nativeQuery = true)
    Page<Operacion> findOperacionesByOperadorAndEstadoCompletado(@Param("idOperador") Long idOperador,
                                                                 Pageable pageable);

}
