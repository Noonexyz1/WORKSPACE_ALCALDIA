package com.prototipo.infrastructure.persistence.db.repository;

import com.prototipo.infrastructure.persistence.db.entity.UnidadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnidadRepository extends JpaRepository<UnidadEntity, Long> {
    // Meto-do para buscar unidades por dirección
    //List<UnidadEntity> findByDireccion(String direccion);
}
