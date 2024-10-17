package com.prototipo.infrastructure.persistence.db.repository;

import com.prototipo.infrastructure.persistence.db.entity.OperacionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OperacionRepository extends JpaRepository<OperacionEntity, Long> {

    //TODO, estas consultas estan mal
    @Query(value =
            """
            SELECT *
            FROM operacion o
            WHERE fk_solicitud_id IN (
                SELECT s.id
                FROM solicitud s
                WHERE s.fk_unidad_id IN (
                    SELECT uu.fk_unidad_id
                    FROM usuario_unidad uu
                    WHERE uu.fk_usuario_id = :idOperador
                    AND uu.is_active = TRUE
                )
            )
            AND o.estado_by_operador = 'Pendiente'
            AND o.estado_cambio = 0
            """, nativeQuery = true)
    Page<OperacionEntity> findOperacionesByOperadorAndEstadoPendiente(@Param("idOperador") Long idOperador,
                                                                      Pageable pageable);


    //TODO, estas consultas estan mal
    @Query(value =
            """
            SELECT *
            FROM operacion o
            WHERE fk_solicitud_id IN (
                SELECT s.id
                FROM solicitud s
                WHERE s.fk_unidad_id IN (
                    SELECT uu.fk_unidad_id
                    FROM usuario_unidad uu
                    WHERE uu.fk_usuario_id = :idOperador
                    AND uu.is_active = TRUE
                )
            )
            AND o.estado_by_operador = 'Iniciado'
            AND o.estado_cambio = 1
            """, nativeQuery = true)
    Page<OperacionEntity> findOperacionesByOperadorAndEstadoIniciado(@Param("idOperador") Long idOperador,
                                                                     Pageable pageable);


    //TODO, estas consultas estan mal
    @Query(value =
            """
            SELECT *
            FROM operacion o
            WHERE fk_solicitud_id IN (
                SELECT s.id
                FROM solicitud s
                WHERE s.fk_unidad_id IN (
                    SELECT uu.fk_unidad_id
                    FROM usuario_unidad uu
                    WHERE uu.fk_usuario_id = :idOperador
                    AND uu.is_active = TRUE
                )
            )
            AND o.estado_by_operador = 'Completado'
            AND o.estado_cambio = 2
            """, nativeQuery = true)
    Page<OperacionEntity> findOperacionesByOperadorAndEstadoCompletado(@Param("idOperador") Long idOperador,
                                                                       Pageable pageable);

}
