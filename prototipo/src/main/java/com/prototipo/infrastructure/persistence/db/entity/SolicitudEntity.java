package com.prototipo.infrastructure.persistence.db.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
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
    private String cite;

    //-estos atributos no seran utilizados--
    /*private Long nroDeCopias;
    private Long nroDePaginas;
    private String tipoDeDocumento;*/
    //---------------------------------------

    /*private String tamanoPagina;
    private String anversoReverso;
    private String colorFotocopia;*/

    @ManyToOne
    private UsuarioEntity fkSolicitante;
    @ManyToOne
    private UsuarioEntity fkResponsable;
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
    @OneToMany(mappedBy = "fkSolicitud")
    private List<DetalleSolicitudEntity> listDetalleSolicitud;

}
