package com.prototipo.infrastructure.persistence.db.repository;

import com.prototipo.infrastructure.persistence.db.entity.SolicitudEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitudRepository extends JpaRepository<SolicitudEntity, Long> {

    @Query(value =
            """
            SELECT *
            FROM solicitud s
            WHERE fk_usuario_solicitante_id = :idUsuarioUnidad
            AND s.is_active = 1
            AND s.autori_flag = 0;
            """, nativeQuery = true)
    List<SolicitudEntity> findAllByIdUserUnidad(@Param("idUsuarioUnidad") Long idUsuarioUnidad);

    @Query(value =
            """
            SELECT *
            FROM solicitud s
            WHERE fk_usuario_solicitante_id IN (
                SELECT id
                FROM usuario_unidad uu
                WHERE fk_responsable_id = :idResponsable
            )
            AND autori_flag = 0
            AND is_active = 1;
            """, nativeQuery = true)
    List<SolicitudEntity> findAllSoliByIdResponsable(@Param("idResponsable") Long idResponsable);

    @Query(value =
            """
            SELECT *
            FROM solicitud s
            WHERE fk_usuario_solicitante_id = :idUsuarioUnidad
            AND s.id IN (
                SELECT a.fk_solicitud_id
                FROM solicitud s , autorizacion a
                WHERE s.id = a.fk_solicitud_id
                AND s.is_active = 1
                AND s.autori_flag = 1
                AND a.finali_flag = 0
            )
            """, nativeQuery = true)
    List<SolicitudEntity> findAllAutoriByIdUserUnidad(@Param("idUsuarioUnidad") Long idUsuarioUnidad);

    @Query(value =
            """
            SELECT *
            FROM solicitud s
            WHERE fk_usuario_solicitante_id = :idUsuarioUnidad
            AND s.id IN (
                SELECT a.fk_solicitud_id
                FROM solicitud s , autorizacion a
                WHERE s.id = a.fk_solicitud_id
                AND s.is_active = 1
                AND s.autori_flag = 1
                AND a.finali_flag = 1
            )
            """, nativeQuery = true)
    List<SolicitudEntity> findAllFinaliByIdUserUnidad(@Param("idUsuarioUnidad") Long idUsuarioUnidad);
}
