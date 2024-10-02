package com.prototipo.infrastructure.persistence.db.repository;

import com.prototipo.infrastructure.persistence.db.entity.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {

    /*Este méto-do busca todas las solicitudes cuyo campo fkSolicitante
    * (que es una relación con la entidad Usuario) tenga un id igual al
    * valor proporcionado. La sintaxis FkSolicitante_Id le indica a
    * Spring Data JPA que debe usar el campo id de la entidad Usuario
    * relacionada con fkSolicitante. */
    List<Solicitud> findAllByFkSolicitante_Id(Long fkSolicitanteId);
    /*Si no hay elementos coincidentes, retorna una lista vacía ([])
    * Este comportamiento es predeterminado en las listas devueltas por
    * métodos de repositorio en Spring Data JPA: nunca se retorna null,
    * siempre es una lista vacía si no hay resultados. */

    List<Solicitud> findAllByFkUnidad_Id(Long fkUnidadId);
}
