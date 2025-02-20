package com.prototipo.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Finalizacion {
    private Long id;
    private String fecha;
    private Autorizacion fkAutorizacion;
}
