package com.prototipo.infrastructure.persistence.db.repository;

import com.prototipo.infrastructure.persistence.db.entity.Credencial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CredencialRepository extends JpaRepository<Credencial, Long> {

    @Query(value = "SELECT * FROM credencial c WHERE c.correo = :correo AND c.pass = :password", nativeQuery = true)
    Optional<Credencial> findByUsernameAndPassword(@Param("correo") String correo,
                                                   @Param("password") String password);
}

