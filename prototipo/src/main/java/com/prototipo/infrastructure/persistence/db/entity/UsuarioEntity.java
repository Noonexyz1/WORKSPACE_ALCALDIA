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
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;
    private String nombres;
    private String apellidos;
    private String correo;

    @OneToOne(mappedBy = "fkUsuario")
    private CredencialEntity credencial;
    @OneToMany(mappedBy = "fkSolicitante")
    private List<SolicitudEntity> listaSolicitantes;
    @OneToMany(mappedBy = "fkResponsable")
    private List<AprobacionEntity> listaAprobacion;
    @OneToMany(mappedBy = "fkOperador")
    private List<OperacionEntity> listaOperaciones;
}
