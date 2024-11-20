package com.prototipo.infrastructure.rest.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SolicitudRequest {
    private Long fkUsuarioSolicitante;
    private String cite;
    private String descripcion;
    // private String fecha; // Comentado porque está comentado en el JSON
    private List<DetalleSolicitudRequest> listDetalleSolicitud;
}
