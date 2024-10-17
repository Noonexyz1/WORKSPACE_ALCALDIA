package com.prototipo.infrastructure.persistence.db.repository;

import com.prototipo.infrastructure.persistence.db.entity.SolicitudEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitudRepository extends JpaRepository<SolicitudEntity, Long> {
    /*Este méto-do busca todas las solicitudes cuyo campo fkSolicitante
    * (que es una relación con la entidad Usuario) tenga un id igual al
    * valor proporcionado. La sintaxis FkSolicitante_Id le indica a
    * Spring Data JPA que debe usar el campo id de la entidad Usuario
    * relacionada con fkSolicitante. */
    List<SolicitudEntity> findAllByFkSolicitante_Id(Long fkSolicitanteId, Pageable pageable);
    /*Si no hay elementos coincidentes, retorna una lista vacía ([])
    * Este comportamiento es predeterminado en las listas devueltas por
    * métodos de repositorio en Spring Data JPA: nunca se retorna null,
    * siempre es una lista vacía si no hay resultados. */

//    List<SolicitudEntity> findAllByFkUnidad_Id(Long fkUnidadId);
//    SolicitudEntity findByFkUnidad_Id(Long fkUnidadId);
}
