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
    List<Object[]> getListReporte(@Param("idSolicitud") Long idSolicitud);

    @Query(value =
            """
            SELECT
                u.nombre_unidad,
                s.cite,
                f.nombre_documento,
                f.nro_paginas,
                dr.nro_retiro,
                sf.tamano,
                sf.color,
                sf.anver_rever,
                sf.precio_ref,
                dr.precio_parcial
            FROM fotocopia f
            JOIN solicitud s ON s.id = f.fk_solicitud_id
            JOIN serv_fotocopia sf ON f.fk_servicio_fotocopia_id = sf.id
            JOIN documento_retiro dr ON f.id = dr.fk_fotocopia_id
            JOIN usuario_unidad uu ON uu.id = s.fk_usuario_solicitante_id
            JOIN unidad u ON u.id = uu.fk_unidad_id
            WHERE RIGHT(dr.fecha, 7) = :mesAnio
            AND dr.total_usado != 0
            """, nativeQuery = true)
    List<Object[]> getListReporteMensual(@Param("mesAnio") String mesAnio);

    @Query(value =
            """
            SELECT
                usuarioDi.formacion,
                usuarioDi.nombres,
                usuarioDi.paterno,
                usuarioDi.materno,
                usuarioSoli.formacion,
                usuarioSoli.nombres,
                usuarioSoli.paterno,
                usuarioSoli.materno,
                cargoDir.nombre_cargo,
                cargoSoli.nombre_cargo,
                s.cite,
                s.fecha,
                s.copia_total,
                unidadSoli.nombre_unidad,
                s.descripcion
            FROM usuario_unidad uu
            JOIN unidad unidadSoli ON unidadSoli.id = uu.fk_unidad_id
            JOIN usuario usuarioSoli ON usuarioSoli.id = uu.fk_usuario_id
            JOIN usuario_unidad usuarioDire ON usuarioDire.id = uu.fk_director_id
            JOIN unidad unidadDire ON unidadDire.id = usuarioDire.fk_unidad_id
            JOIN usuario usuarioDi ON usuarioDi.id = usuarioDire.fk_usuario_id
            JOIN solicitud s ON s.fk_usuario_solicitante_id = uu.id
            JOIN cargo cargoSoli ON cargoSoli.id = uu.fk_cargo_id
            JOIN cargo cargoDir ON cargoDir.id = usuarioDire.fk_cargo_id
            WHERE s.id = :idSolicitud
            """, nativeQuery = true)
    Object[][] getInformeReport(@Param("idSolicitud") Long idSolicitud);
}
