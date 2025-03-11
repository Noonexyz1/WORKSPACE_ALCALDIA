package com.prototipo.infrastructure.http.rest.model.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AutorizacionResponse {

    private Long idAutorizacion;

    private Long idSolicitud;
    private String cite;
    private String fecha;
    private String nomCompleto;
    private String nomCargo;
    private String nombreUnidad;

    private Long totalAutorizado;
    private BigDecimal totalCotizadoBs;
    private String fechaCotizado;
}
