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

    @ManyToOne
    private Unidad unidadTrabajo;
    @ManyToOne
    private Unidad responsableUnidad;
    @ManyToOne
    private Rol rol;

    @OneToMany(mappedBy = "usuario")
    private List<Credencial> listaDeCredenciales;
    @OneToMany(mappedBy = "usuarioEnvio")
    private List<Solicitud> listaUsuarioSoli;
    @OneToMany(mappedBy = "usuario")
    private List<HistorialUsuario> listaHistoriales;
    @OneToMany(mappedBy = "usuario")
    private List<Aprobacion> listaAprobacion;
    @OneToMany(mappedBy = "usuario")
    private List<Operacion> listaOperaciones;

}
