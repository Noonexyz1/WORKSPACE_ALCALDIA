package com.prototipo.application.modelDto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServicioFotocopiaDto {
    private Long id;
    private String color;
    private String tamano;
    private String anverRever;
    private Double precioRef;
    private Boolean isActive;
}
