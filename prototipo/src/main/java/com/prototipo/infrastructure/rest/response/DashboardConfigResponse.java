package com.prototipo.infrastructure.rest.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardConfigResponse {

    private Long id;

    private String nombreComponente;
    private String datosCompononente;
}
