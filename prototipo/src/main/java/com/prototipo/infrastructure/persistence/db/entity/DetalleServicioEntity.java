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
@Table(name = "detalle_servicio")
public class DetalleServicioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;
    private String color;
    private String tamano;
    private String anverRever;
    private Double precioRef;
    private Boolean isActive;

    @OneToMany(mappedBy = "fkDetalleServicio")
    private List<CotizacionEntity> listCotizacion;

}
