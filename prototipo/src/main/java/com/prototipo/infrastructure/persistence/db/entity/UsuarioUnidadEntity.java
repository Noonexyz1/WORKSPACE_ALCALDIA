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
    @ManyToOne
    private CargoEntity fkCargo;

    //------------------Relacion Reflexiva Responsable--
    @ManyToOne
    private UsuarioUnidadEntity fkResponsable;
    @OneToMany(mappedBy = "fkResponsable")
    private List<UsuarioUnidadEntity> listFuncionarios;
    //--------------------------------------------------

    //------------------Relacion Reflexiva Responsable--
    @ManyToOne
    private UsuarioUnidadEntity fkDirector;
    @OneToMany(mappedBy = "fkDirector")
    private List<UsuarioUnidadEntity> listFuncionariosSistema;
    //--------------------------------------------------

    @OneToMany(mappedBy = "fkUsuarioSolicitante")
    private List<SolicitudEntity> listSolicitud;
    @OneToMany(mappedBy = "fkUsuarioResponsable")
    private List<AutorizacionEntity> listAutorizacion;
}
