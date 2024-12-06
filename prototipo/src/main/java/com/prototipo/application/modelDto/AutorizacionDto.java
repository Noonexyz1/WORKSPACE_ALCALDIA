package com.prototipo.application.modelDto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AutorizacionDto {

    private Long id;
    private String fecha;
    private Long totalAutorizado;
    private BigDecimal totalCotizadoBs;
    private Long finaliFlag;

    private UsuarioUnidadDto fkUsuarioResponsable;
    private SolicitudDto fkSolicitud;
}
