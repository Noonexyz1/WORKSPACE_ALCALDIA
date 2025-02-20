package com.prototipo.infrastructure.persistence.db.entity;

import jakarta.persistence.*;
import lombok.*;

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

    @OneToOne
    private AutorizacionEntity fkAutorizacion;
}
