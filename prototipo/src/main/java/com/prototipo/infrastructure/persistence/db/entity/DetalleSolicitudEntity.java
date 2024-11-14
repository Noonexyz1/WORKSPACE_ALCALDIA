package com.prototipo.infrastructure.persistence.db.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "detalle_solicitud")
public class DetalleSolicitudEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    private String nombreDocumento;
    private Long nroPaginas;
    private Long nroCopias;

    private String tamanoPagina;
    private String anversoReverso;
    private String colorFotocopia;

    @ManyToOne
    private SolicitudEntity fkSolicitud;

    @OneToMany(mappedBy = "fkDetalleSolicitud")
    private List<DescargoEntity> listDescargo;
}
