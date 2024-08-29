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
    //private DPF archivoParaFotocopiar;


    //los estados posibles son Pendiente, Aprobada y Rechazada
    private String notificacionToAprobar;


    @ManyToOne
    private Usuario fk_solicitante;

    @OneToOne(mappedBy = "fk_solicitud")
    private ArchivoPdf archivoPdf;

    @OneToMany(mappedBy = "fk_solicitud")
    private List<Aprobacion> listaAprobacion;



}
