package com.prototipo.application.modelDto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinalizacionDto {
    private Long id;
    private String fecha;
    private AutorizacionDto fkAutorizacion;
}
