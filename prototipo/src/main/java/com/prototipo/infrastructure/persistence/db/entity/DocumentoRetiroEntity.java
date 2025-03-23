package com.prototipo.infrastructure.persistence.db.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "documento_retiro")
public class DocumentoRetiroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;
    private Long totalCopia;
    private Long totalUsado;
    private Long totalDisponible;

    private String fecha;

    @ManyToOne
    private FotocopiaEntity fkFotocopia;
}
