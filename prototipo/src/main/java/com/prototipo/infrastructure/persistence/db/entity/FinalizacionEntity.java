package com.prototipo.infrastructure.persistence.db.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "finalizacion")
public class FinalizacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;
    private String fecha;
    private Long totalEjecutado;
    private BigDecimal totalEjecutadoBs;

    @OneToOne
    private AutorizacionEntity fkAutorizacion;
}
