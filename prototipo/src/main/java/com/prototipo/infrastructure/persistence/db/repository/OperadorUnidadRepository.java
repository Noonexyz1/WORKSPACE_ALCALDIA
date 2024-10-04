package com.prototipo.infrastructure.persistence.db.repository;

import com.prototipo.infrastructure.persistence.db.entity.OperadorUnidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperadorUnidadRepository extends JpaRepository<OperadorUnidad, Long> {
    // Meto-do para encontrar por ID del operador (usuario)
    List<OperadorUnidad> findByFkUsuario_Id(Long idOperador);
}
