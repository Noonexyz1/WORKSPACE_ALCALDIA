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

    // Relación reflexiva: un empleado puede tener un gerente
    @ManyToOne
    private Usuario fkResponsable;
    @ManyToOne
    private Rol fkRol;

    // Relación reflexiva: un gerente puede tener múltiples empleados a su cargo
    @OneToMany(mappedBy = "fkResponsable")
    private List<Usuario> listEmpleadosACargo;
    @OneToOne(mappedBy = "fkUsuario")
    private Credencial credencial;
    @OneToMany(mappedBy = "fkSolicitante")
    private List<Solicitud> listaSolicitantes;
    @OneToMany(mappedBy = "fkSolicitante")
    private List<Aprobacion> listaAprobacion;
    @OneToMany(mappedBy = "fkOperador")
    private List<Aprobacion> listaOperaciones;
}
