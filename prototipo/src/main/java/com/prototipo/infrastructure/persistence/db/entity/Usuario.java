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
    private Usuario fk_responsable;

    // Relación reflexiva: un gerente puede tener múltiples empleados a su cargo
    @OneToMany(mappedBy = "fk_responsable")
    private List<Usuario> listEmpleadosACargo;


    @ManyToOne
    private Unidad fk_unidad;


    @OneToOne(mappedBy = "fk_usuario")
    private Credencial credencial;

    @ManyToOne
    private Rol fk_rol;

    @OneToMany(mappedBy = "fk_solicitante")
    private List<Solicitud> listaSolicitantes;

    @OneToMany(mappedBy = "fk_solicitante")
    private List<Aprobacion> listaAprobacion;







    @OneToMany(mappedBy = "fk_operador")
    private List<Aprobacion> listaOperaciones;

}
