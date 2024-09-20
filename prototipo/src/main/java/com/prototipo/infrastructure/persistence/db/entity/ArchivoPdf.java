package com.prototipo.infrastructure.persistence.db.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "archivo_pdf")
public class ArchivoPdf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    //@Column(columnDefinition = "LONGTEXT")
    @Column(columnDefinition = "MEDIUMTEXT")
    private String archivo;

    @ManyToOne
    private Solicitud fk_solicitud;
}
