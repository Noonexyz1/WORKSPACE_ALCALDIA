package com.prototipo.infrastructure.persistence.db.repository;

import com.prototipo.infrastructure.persistence.db.entity.AutorizacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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
            WHERE fk_usuario_responsable_id = :idResponsable
            AND finali_flag = 0
            """, nativeQuery = true)
    List<AutorizacionEntity> buscarAutorizacionByIdResponsable(@Param("idResponsable") Long idResponsable);
}
