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
@Table(name = "usuario_unidad")
public class UsuarioUnidadEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;
    private Boolean isActive;

    @ManyToOne
    private UsuarioEntity fkUsuario;
    @ManyToOne
    private UnidadEntity fkUnidad;
    @ManyToOne
    private RolEntity fkRol;
}
