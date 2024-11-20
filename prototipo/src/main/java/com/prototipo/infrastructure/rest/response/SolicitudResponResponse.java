package com.prototipo.infrastructure.rest.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SolicitudResponResponse {
    private Long id;
    private String cite;
    private String fecha;
    private String nomCompleto;
    private String nomCargo;
    private String nombreUnidad;
}
