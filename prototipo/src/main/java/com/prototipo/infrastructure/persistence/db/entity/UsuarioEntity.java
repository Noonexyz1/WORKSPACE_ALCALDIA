package com.prototipo.infrastructure.persistence.db.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario")
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;
    private String nombres;
    private String paterno;
    private String materno;
    private String correo;
    private String ci;
    private String formacion;

    @OneToOne(mappedBy = "fkUsuario")
    private CredencialEntity credencial;
}
