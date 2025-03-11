package com.prototipo.infrastructure.http.rest.model.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UnidadResponse {
    private Long id;
    private String nombre;
    private String direccion;
}
