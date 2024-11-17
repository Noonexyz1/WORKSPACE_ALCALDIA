package com.prototipo.infrastructure.persistence.db.repository;

import com.prototipo.application.modelDto.NotaDePedidoDto;
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
            SELECT ds.nro_copias,
            ds.nombre_documento,
            d.precio_unitario,
            d.precio_total
            FROM detalle_solicitud ds, descargo d
            WHERE ds.id = d.fk_detalle_solicitud_id
            AND ds.fk_solicitud_id = :idSolicitud
            """, nativeQuery = true)
    List<NotaDePedidoDto> getNotaDePedido(@Param("idSolicitud") Long idSolicitud);

    @Query(value =
            """
            SELECT ds.nro_copias,
            d.precio_unitario,
            d.precio_total
            FROM detalle_solicitud ds, descargo d
            WHERE ds.id = d.fk_detalle_solicitud_id
            AND ds.fk_solicitud_id = :idSolicitud
            """, nativeQuery = true)
    List<ReporteDto> getListReporte(@Param("idSolicitud") Long idSolicitud);
}
