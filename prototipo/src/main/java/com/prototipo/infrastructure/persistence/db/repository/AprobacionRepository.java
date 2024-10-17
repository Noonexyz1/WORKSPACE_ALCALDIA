package com.prototipo.infrastructure.persistence.db.repository;

import com.prototipo.infrastructure.persistence.db.entity.AprobacionEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AprobacionRepository extends JpaRepository<AprobacionEntity, Long> {

    //TODO, probar esta consulta
    @Query(value =
            """
            SELECT *
            FROM aprobacion a
            WHERE a.fk_solicitud_id IN (
                SELECT s.id
                FROM solicitud s
                WHERE s.fk_unidad_id = (
                    SELECT uu.fk_unidad_id
                    FROM usuario_unidad uu
                    WHERE uu.fk_usuario_id = :idRespon
                    AND uu.is_active = TRUE
                )
            )
            AND a.estado_by_responsable = 'Pendiente'
            AND a.estado_cambio = 0
            """, nativeQuery = true)
    List<AprobacionEntity> findAprobacionesByResponPendiente(@Param("idRespon") Long idRespon, Pageable pageable);
    //Page<Aprobacion> findAprobacionesByRepon(Long idRespon, Pageable pageable);


    @Query(value =
            """
            SELECT * #a.fk_solicitud_id
            FROM aprobacion a
            WHERE a.fk_solicitud_id IN (
                SELECT s.id
                FROM solicitud s
                WHERE s.fk_unidad_id = (
                    SELECT uu.fk_unidad_id
                    FROM usuario_unidad uu
                    WHERE uu.fk_usuario_id = :idRespon
                    AND uu.is_active = TRUE
                )
            )
            AND a.estado_by_responsable = 'Aprobada'
            AND a.estado_cambio = 1
            """, nativeQuery = true)
    List<AprobacionEntity> findAprobacionesByResponAprobadas(@Param("idRespon") Long idRespon, Pageable pageable);
    //Page<Aprobacion> findAprobacionesByRepon(Long idRespon, Pageable pageable);


    @Query(value =
            """
            SELECT * #a.fk_solicitud_id
            FROM aprobacion a
            WHERE a.fk_solicitud_id IN (
                SELECT s.id
                FROM solicitud s
                WHERE s.fk_unidad_id = (
                    SELECT uu.fk_unidad_id
                    FROM usuario_unidad uu
                    WHERE uu.fk_usuario_id = :idRespon
                    AND uu.is_active = TRUE
                )
            )
            AND a.estado_by_responsable = 'Rechazada'
            AND a.estado_cambio = 1
            """, nativeQuery = true)
    List<AprobacionEntity> findAprobacionesByResponRechazadas(@Param("idRespon") Long idRespon, Pageable pageable);
    //Page<Aprobacion> findAprobacionesByRepon(Long idRespon, Pageable pageable);

/*
    List<AprobacionEntity> findByfkSolicitud_Id(Long fkSolicitudId);

    //@Query("SELECT a FROM Aprobacion a WHERE a.fkSolicitud.fkUnidad.nombre = :nombreUnidad")
    List<AprobacionEntity> findAprobacionesByUnidadNombre(String nombreUnidad);
 */

    @Query(value =
            """
            SELECT *
            FROM aprobaciones
            WHERE aprobaciones.id = :nombreClave
            AND aprobaciones.nombre = 'campo_valor'
            """, nativeQuery = true)
    List<String> listaDeArchivosdeDocumentos(@Param("nombreClave") String nombreClave);
}
