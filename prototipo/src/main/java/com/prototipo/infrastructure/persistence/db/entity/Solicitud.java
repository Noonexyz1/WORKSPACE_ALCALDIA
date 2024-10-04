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
public class Solicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;
    private Long nroDeCopias;
    private String tipoDeDocumento;
    private Long nroDePaginas;


    @ManyToOne
    private Usuario fkSolicitante;
    @ManyToOne
    private Unidad fkUnidad;

    @OneToMany(mappedBy = "fkSolicitud")
    private List<Aprobacion> listAprobaciones;

    @OneToMany(mappedBy = "fkSolicitud")
    private List<ArchivoPdf> archivoPdf;
    @OneToMany(mappedBy = "fkSolicitud")
    private List<GastoInsumo> listGastoInsumos;
    @OneToMany(mappedBy = "fkSolicitud")
    private List<Aprobacion> listaAprobacion;
    @OneToMany(mappedBy = "fkSolicitud")
    private List<Operacion> listaOperacion;
}
