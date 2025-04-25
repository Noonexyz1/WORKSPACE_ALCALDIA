package com.prototipo.infrastructure.persistence.db.repository;

import com.prototipo.infrastructure.persistence.db.entity.FotocopiaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotaDePedidoRepository extends JpaRepository<FotocopiaEntity, Long> {
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
}
