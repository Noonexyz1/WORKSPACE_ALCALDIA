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
@Table(name = "aprobacion")
public class AprobacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;
    private String estadoByResponsable;

    //Esta atributo es para actualizar si se a detectado un cambio
    //Se tiene que hacer un update si esta solicitud ya se a detectado un cambio
    //Esto se tiene que actualziar tras despues de cada operacion
    //0 = esto significa que esta aprobacion no ha sufrido un estado de cambio
    //1 = esto significa que esta aprobacion ya ha sufrido un cambio
    private Boolean estadoCambio;

    @ManyToOne
    private SolicitudEntity fkSolicitud;
    @ManyToOne
    private UsuarioEntity fkResponsable;

    //@OneToMany(mappedBy = "fkAprobacion")
    //private List<Operacion> listOperaciones;
}
