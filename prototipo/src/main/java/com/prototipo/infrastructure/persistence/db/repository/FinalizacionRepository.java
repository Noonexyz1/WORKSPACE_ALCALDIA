package com.prototipo.infrastructure.persistence.db.repository;

import com.prototipo.infrastructure.persistence.db.entity.FinalizacionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FinalizacionRepository extends JpaRepository<FinalizacionEntity, Long> {

    @Query(value =
            """
            SELECT *
            FROM finalizacion f
            WHERE f.fk_autorizacion_id = (
                SELECT a.id
                FROM solicitud s , autorizacion a
                WHERE s.id = a.fk_solicitud_id
                AND s.is_active = 1
                AND s.autori_flag = 1
                AND a.finali_flag = 1
                AND s.id = :id
            )
            """, nativeQuery = true)
    Page<FinalizacionEntity> findFinalizacionSoliById(@Param("id") Long id, Pageable pageable);
}
