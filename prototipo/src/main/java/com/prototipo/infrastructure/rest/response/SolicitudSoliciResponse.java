package com.prototipo.infrastructure.rest.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SolicitudSoliciResponse {
    private String cite;
    private String fecha;
    private String descripcion;
    private String cargo;
    private String ci;
}
