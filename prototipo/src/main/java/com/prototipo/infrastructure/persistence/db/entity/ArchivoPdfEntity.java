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
public class ArchivoPdfEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;
    private String nombreArchivo;

    //@Column(columnDefinition = "LONGTEXT")
    @Column(columnDefinition = "MEDIUMTEXT")
    private String archivo;

    @ManyToOne
    private SolicitudEntity fkSolicitud;
}
