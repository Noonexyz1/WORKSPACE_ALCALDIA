package com.prototipo.infrastructure.persistence.db.repository;

import com.prototipo.infrastructure.persistence.db.entity.DetalleSolicitudEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleSolicitudRepository extends JpaRepository<DetalleSolicitudEntity, Long> {

    @Query(value =
            """
            SELECT *
            FROM detalle_solicitud
            WHERE fk_solicitud_id = :idSolicitud
            """, nativeQuery = true)
    List<DetalleSolicitudEntity> findAllBySolicitudId(@Param("idSolicitud") Long idSolicitud);
}
