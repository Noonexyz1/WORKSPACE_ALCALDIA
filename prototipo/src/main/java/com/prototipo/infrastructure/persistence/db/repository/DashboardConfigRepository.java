package com.prototipo.infrastructure.persistence.db.repository;

import com.prototipo.infrastructure.persistence.db.entity.DashboardConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DashboardConfigRepository extends JpaRepository<DashboardConfig, Long> {
    DashboardConfig findByFkRol_Id(Long idFkRol);
}
