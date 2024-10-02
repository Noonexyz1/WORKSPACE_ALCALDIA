package com.prototipo.infrastructure.persistence.db.repository;

import com.prototipo.infrastructure.persistence.db.entity.Aprobacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AprobacionRepository extends JpaRepository<Aprobacion, Long> {
    List<Aprobacion> findByfkSolicitud_Id(Long fkSolicitudId);

    @Query("SELECT a FROM Aprobacion a WHERE a.fkSolicitud.fkUnidad.nombre = :nombreUnidad")
    List<Aprobacion> findAprobacionesByUnidadNombre(String nombreUnidad);
}
