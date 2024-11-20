package com.prototipo.domain.model;

import com.prototipo.infrastructure.persistence.db.entity.FinalizacionEntity;
import com.prototipo.infrastructure.persistence.db.entity.SolicitudEntity;
import com.prototipo.infrastructure.persistence.db.entity.UsuarioUnidadEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Autorizacion {

    private Long id;
    private String fecha;
    private Long totalAutorizado;
    private BigDecimal totalCotizadoBs;

    private UsuarioUnidad fkUsuarioResponsable;
    private Solicitud fkSolicitud;
}
