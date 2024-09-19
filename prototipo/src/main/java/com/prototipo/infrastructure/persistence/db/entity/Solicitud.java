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
    //private TipoDocumento tipoDeDocumento;
    private String tipoDeDocumento;
    private Long nroDePaginas;
    //private Unidad nombreUnidad;

    //private EstadoSolicitud estadoSolicitud;
    private String estadoSolicitud;
    //private DPF archivoParaFotocopiar;


    //los estados posibles son Pendiente, Aprobada y Rechazada
    private String notificacionToAprobar;


    @ManyToOne
    private Usuario fk_solicitante;
    @ManyToOne
    private Unidad fk_unidad;


    @OneToMany(mappedBy = "fk_solicitud")
    private List<ArchivoPdf> archivoPdf;
    @OneToMany(mappedBy = "fk_solicitud")
    private List<Aprobacion> listaAprobacion;
    @OneToMany(mappedBy = "fk_solicitud")
    private List<GastoInsumo> listGastoInsumos;
}
