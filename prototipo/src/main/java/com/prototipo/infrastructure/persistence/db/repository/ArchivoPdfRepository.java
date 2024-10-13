package com.prototipo.infrastructure.persistence.db.repository;

import com.prototipo.infrastructure.persistence.db.entity.ArchivoPdf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArchivoPdfRepository extends JpaRepository<ArchivoPdf, Long> {
    List<ArchivoPdf> findAllByFkSolicitud_Id(Long id);
}
