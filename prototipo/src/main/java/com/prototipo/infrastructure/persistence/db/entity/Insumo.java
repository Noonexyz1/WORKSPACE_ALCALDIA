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
@Table(name = "insumo")
public class Insumo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;
    private String tipo_reporte;
    private String fecha_reporte;
    private String formato; //ENUM('PDF', 'Excel') NOT NULL
    private String detalle;

    /*@OneToMany(mappedBy = "fk_insumo")
    private List<Aprobacion> listaAprobaciones;*/
    @OneToMany(mappedBy = "fkInsumo")
    private List<GastoInsumo> listGastoInsumos;
}
