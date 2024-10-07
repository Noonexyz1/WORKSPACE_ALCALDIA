package com.prototipo.infrastructure.persistence.db.repository;

import com.prototipo.infrastructure.persistence.db.entity.Aprobacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AprobacionRepository extends JpaRepository<Aprobacion, Long> {
    List<Aprobacion> findByfkSolicitud_Id(Long fkSolicitudId);

    @Query("SELECT a FROM Aprobacion a WHERE a.fkSolicitud.fkUnidad.nombre = :nombreUnidad")
    List<Aprobacion> findAprobacionesByUnidadNombre(String nombreUnidad);

    @Query(value =
            """
            SELECT *
            FROM aprobacion a
            WHERE a.fk_solicitud_id = (
                SELECT s.id
                FROM solicitud s
                WHERE s.fk_unidad_id = (
                    SELECT u.id
                    FROM unidad u
                    WHERE u.id = (
                        SELECT r.fk_unidad_id
                        FROM responsable r
                        #Los ids de responables
                        WHERE r.fk_usuario_id = :idRespon
                        AND r.is_active = TRUE
                    )
                )
            )
            AND a.estado_by_responsable = "Pendiente"
            """, nativeQuery = true)
    List<Aprobacion> findAprobacionesByRepon(Long idRespon, Pageable pageable);
    //Page<Aprobacion> findAprobacionesByRepon(Long idRespon, Pageable pageable);
}
