package com.prototipo.infrastructure.persistence.db.repository;

import com.prototipo.infrastructure.persistence.db.entity.ServicioFotocopiaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicioFotocopiaRepository extends JpaRepository<ServicioFotocopiaEntity, Long> {

    @Query(value =
            """
            SELECT *
            FROM serv_fotocopia sf
            WHERE sf.anver_rever = :anverRever
            AND sf.color = :color
            AND sf.tamano = :tamano
            AND sf.is_active = TRUE;
            """, nativeQuery = true)
    ServicioFotocopiaEntity findByAnverColorTam(
            @Param("anverRever") String anverRever,
            @Param("color") String color,
            @Param("tamano") String tamano);
}
