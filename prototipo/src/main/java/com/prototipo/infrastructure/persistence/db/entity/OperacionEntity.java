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
@Table(name = "operacion")
public class OperacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;
    private String estadoByOperador;

    //Esta atributo es para actualizar si se a detectado un cambio
    //Se tiene que hacer un update si esta solicitud ya se a detectado un cambio
    //Esto se tiene que actualziar tras despues de cada operacion
    //0 = esto significa que esta Operacion no ha sufrido un estado de cambio
    //1 = esto significa que esta Operacion ya ha sufrido un cambio
    private Integer estadoCambio;

    @ManyToOne
    private SolicitudEntity fkSolicitud;
    //@ManyToOne(fetch = FetchType.EAGER)
    @ManyToOne
    private UsuarioEntity fkOperador;
}
