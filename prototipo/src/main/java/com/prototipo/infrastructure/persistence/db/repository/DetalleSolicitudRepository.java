package com.prototipo.infrastructure.persistence.db.repository;

import com.prototipo.infrastructure.persistence.db.entity.FotocopiaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleSolicitudRepository extends JpaRepository<FotocopiaEntity, Long> {

    @Query(value =
            """
            SELECT *
            FROM fotocopia f
            WHERE f.fk_solicitud_id = :idSolicitud;
            """, nativeQuery = true)
    List<FotocopiaEntity> findAllFotocopiaByIdSoli(@Param("idSolicitud") Long idSolicitud);
}
