package com.prototipo.infrastructure.rest.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetalleSolicitudExtendidoResponse {
    private Long idSolicitud;
    private String cite;
    private String fecha;
    private String descripcion;
    private String nombreServicio;
    private Double precioTotal;

    private List<DetalleSolicitudResponse> detalleSolicitudResponses;
}
