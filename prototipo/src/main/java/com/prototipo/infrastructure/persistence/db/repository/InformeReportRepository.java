package com.prototipo.infrastructure.persistence.db.repository;

import com.prototipo.infrastructure.persistence.db.entity.FotocopiaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InformeReportRepository extends JpaRepository<FotocopiaEntity, Long> {
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
