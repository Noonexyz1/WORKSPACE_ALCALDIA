package com.prototipo.infrastructure.rest.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CargoResponse {
    private Long id;
    private String nombreCargo;
}
