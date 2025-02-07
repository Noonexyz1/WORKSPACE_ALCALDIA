package com.prototipo.infrastructure.persistence.db.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "solicitud")
public class SolicitudEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;
    private String cite;
    private String descripcion;
    private String fecha;
    private Long autoriFlag;
    private Boolean isActive;

    @ManyToOne
    private UsuarioUnidadEntity fkUsuarioSolicitante;

    @OneToOne(mappedBy = "fkSolicitud")
    private AutorizacionEntity autorizacion;
}
