package com.prototipo.infrastructure.persistence.db.repository;

import com.prototipo.application.modelDto.ReporteDto;
import com.prototipo.infrastructure.persistence.db.entity.DetalleSolicitudEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportesPDFRepository extends JpaRepository<DetalleSolicitudEntity, Long> {

    @Query(value =
            """
            SELECT 
                ds.nro_copias AS nroCopias,
                ds.nombre_documento AS nombreDocumento,
                c.precio_unitario AS precioUnitario,
                c.precio_total AS precioTotal
            FROM detalle_solicitud ds
            INNER JOIN cotizacion c ON ds.id = c.fk_detalle_solicitud_id
            WHERE ds.fk_solicitud_id = :idSolicitud
            """, nativeQuery = true)
    List<Object[]> getNotaDePedido(@Param("idSolicitud") Long idSolicitud);



    @Query(value =
            """
            SELECT
            CAST(ds.nro_copias AS int) AS nroCopias,
            d.precio_unitario AS precioUnitario,
            d.precio_total AS precioTotal
            FROM detalle_solicitud ds, cotizacion d
            WHERE ds.id = d.fk_detalle_solicitud_id
            AND ds.fk_solicitud_id = :idSolicitud
            """, nativeQuery = true)
    List<Object[]> getListReporte(@Param("idSolicitud") Long idSolicitud);
}
