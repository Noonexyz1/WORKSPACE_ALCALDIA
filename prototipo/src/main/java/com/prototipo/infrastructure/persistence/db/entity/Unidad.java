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
@Table(name = "unidad")
public class Unidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;
    private String nombre;
    private String direccion;


    @OneToOne(mappedBy = "fk_unidadResponsable")
    private Usuario usuario;


    @OneToMany(mappedBy = "fk_unidad")
    private List<Usuario> listaDeTrabajadores;


}
