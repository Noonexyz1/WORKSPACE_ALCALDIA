package com.prototipo.infrastructure.persistence.db.repository;

import com.prototipo.domain.model.DocumentoRetiro;
import com.prototipo.infrastructure.persistence.db.entity.DocumentoRetiroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentoRetiroRepository extends JpaRepository<DocumentoRetiroEntity, Long> {

    @Query(value = """
            SELECT *
            FROM documento_retiro dr
            WHERE dr.fk_fotocopia_id = :idFotocopia
            ORDER BY dr.id DESC
            LIMIT 1
            """, nativeQuery = true)
    DocumentoRetiro findByFkFotocopia(@Param("idFotocopia") Long idFotocopia);
}
