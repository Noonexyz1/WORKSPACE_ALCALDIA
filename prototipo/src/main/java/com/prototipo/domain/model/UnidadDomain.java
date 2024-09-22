package com.prototipo.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnidadDomain {
    private Long id;
    private String nombre;
    private String direccion;
}
