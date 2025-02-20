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
@Table(name = "fotocopia")
public class FotocopiaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    private String nombreDocumento;
    private Long nroPaginas;
    private Long nroCopias;

    private Double precioDocu;

    @ManyToOne
    private SolicitudEntity fkSolicitud;

    @ManyToOne
    private ServicioFotocopiaEntity fkServicioFotocopia;
}
