package com.prototipo.infrastructure.persistence.db.repository;

import com.prototipo.infrastructure.persistence.db.entity.ArchivoPdfEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArchivoPdfRepository extends JpaRepository<ArchivoPdfEntity, Long> {
    List<ArchivoPdfEntity> findAllByFkSolicitud_Id(Long id);
}
