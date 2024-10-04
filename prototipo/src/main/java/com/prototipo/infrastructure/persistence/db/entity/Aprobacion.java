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
@Table(name = "aprobacion")
public class Aprobacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;
    private String estadoByResponsable;

    @ManyToOne
    private Solicitud fkSolicitud;
    @ManyToOne
    private Usuario fkResponsable;

    //@OneToMany(mappedBy = "fkAprobacion")
    //private List<Operacion> listOperaciones;
}
