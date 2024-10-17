package com.prototipo.infrastructure.persistence.db.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "solicitud")
public class SolicitudEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;
    private Long nroDeCopias;
    private String tipoDeDocumento;
    private Long nroDePaginas;


    @ManyToOne
    private UsuarioEntity fkSolicitante;
    @ManyToOne
    private UnidadEntity fkUnidad;

    @OneToMany(mappedBy = "fkSolicitud")
    private List<AprobacionEntity> listAprobaciones;

    @OneToMany(mappedBy = "fkSolicitud")
    private List<ArchivoPdfEntity> archivoPdf;
    @OneToMany(mappedBy = "fkSolicitud")
    private List<AprobacionEntity> listaAprobacion;
    @OneToMany(mappedBy = "fkSolicitud")
    private List<OperacionEntity> listaOperacion;
}
