package com.prototipo.infrastructure.persistence.db.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "autorizacion")
public class AutorizacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;
    private String fecha;
    private Long totalAutorizado;
    private BigDecimal totalCotizadoBs;

    @ManyToOne
    private UsuarioUnidadEntity fkUsuarioResponsable;
    @OneToOne
    private SolicitudEntity fkSolicitud;

    @OneToOne(mappedBy = "fkAutorizacion")
    private FinalizacionEntity finalizacion;
}
