package com.prototipo.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServicioFotocopia {
    private Long id;
    private String color;
    private String tamano;
    private String anverRever;
    private Double precioRef;
    private Boolean isActive;
}
