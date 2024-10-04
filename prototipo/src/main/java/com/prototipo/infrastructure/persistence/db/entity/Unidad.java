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
@Table(name = "unidad")
public class Unidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;
    private String nombre;
    private String direccion;

    @OneToMany(mappedBy = "fkUnidad")
    private List<Solicitud> listaUsuarios;
    @OneToMany(mappedBy = "fkUnidad")
    private List<Responsable> listaResponsables;
    @OneToMany(mappedBy = "fkUnidad")
    private List<OperadorUnidad> listaOpeUnidad;
}
