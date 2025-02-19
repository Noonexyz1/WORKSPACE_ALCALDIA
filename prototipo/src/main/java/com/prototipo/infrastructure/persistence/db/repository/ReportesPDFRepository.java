package com.prototipo.infrastructure.persistence.db.repository;

import com.prototipo.infrastructure.persistence.db.entity.FotocopiaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportesPDFRepository extends JpaRepository<FotocopiaEntity, Long> {

    @Query(value =
            """
            SELECT
                f.nombre_documento,
                f.nro_paginas,
                f.nro_copias,
                sf.tamano,
                sf.color,
                sf.anver_rever,
                sf.precio_ref,
                f.precio_docu
            FROM solicitud s, fotocopia f, serv_fotocopia sf
            WHERE f.fk_solicitud_id = s.id
            AND f.fk_servicio_fotocopia_id = sf.id
            AND f.fk_solicitud_id = :idSolicitud;
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
