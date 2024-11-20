package com.prototipo.domain.model;

import com.prototipo.infrastructure.persistence.db.entity.AutorizacionEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Finalizacion {

    private Long id;
    private String fecha;
    private Long totalEjecutado;
    private BigDecimal totalEjecutadoBs;
    private Autorizacion fkAutorizacion;
}
