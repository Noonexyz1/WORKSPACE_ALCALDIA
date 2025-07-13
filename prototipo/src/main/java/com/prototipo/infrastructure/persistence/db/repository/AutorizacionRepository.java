package com.prototipo.infrastructure.persistence.db.repository;

import com.prototipo.infrastructure.persistence.db.entity.AutorizacionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorizacionRepository extends JpaRepository<AutorizacionEntity, Long> {

    @Query(value =
            """
            SELECT *
            FROM autorizacion a
            WHERE fk_solicitud_id = :idSolicitud
            """, nativeQuery = true)
    AutorizacionEntity buscarAutorizacionByIdSoli(@Param("idSolicitud") Long idSolicitud);

    @Query(value =
            """
            SELECT *
            FROM autorizacion a
            WHERE a.finali_flag = 0
            AND a.fk_usuario_responsable_id = (
                #ME estan pasando el id de usuario_unidad
                SELECT uu.id
                FROM usuario_unidad uu
                WHERE uu.id = :idResponsable
                AND is_active = TRUE
            )
            """, nativeQuery = true)
    Page<AutorizacionEntity> buscarAutorizacionByIdResponsable(@Param("idResponsable") Long idResponsable, Pageable pageable);

    @Query(value =
            """
            SELECT *
            FROM autorizacion a
            WHERE a.fk_solicitud_id = (
                SELECT s.id
                FROM solicitud s
                WHERE s.id = :id
            )
            AND a.finali_flag = 0
            """, nativeQuery = true)
    Page<AutorizacionEntity> buscarAutorizacionByIdResponsableByIdSoli(@Param("id") Long id, Pageable pageable);
}
