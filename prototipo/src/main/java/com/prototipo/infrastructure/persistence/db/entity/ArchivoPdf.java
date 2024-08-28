package com.prototipo.infrastructure.persistence.db.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "archivo_pdf")
public class ArchivoPdf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;
    private String archivo;

    @OneToOne
    private Solicitud fk_solicitud;
}
