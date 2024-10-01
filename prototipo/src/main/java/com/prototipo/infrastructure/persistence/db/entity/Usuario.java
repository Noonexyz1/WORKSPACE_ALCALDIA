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
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;
    private String nombres;
    private String apellidos;
    private String correo;
    private Boolean isActive;

    @ManyToOne
    private Rol fkRol;

    @OneToOne(mappedBy = "fkUsuario")
    private Credencial credencial;

    @OneToMany(mappedBy = "fkSolicitante")
    private List<Solicitud> listaSolicitantes;

    @OneToMany(mappedBy = "fkResponsable")
    private List<Aprobacion> listaAprobacion;
    @OneToMany(mappedBy = "fkOperador")
    private List<Operacion> listaOperaciones;
    @OneToMany(mappedBy = "fkUsuario")
    private List<Responsable> listaResponsables;
}
