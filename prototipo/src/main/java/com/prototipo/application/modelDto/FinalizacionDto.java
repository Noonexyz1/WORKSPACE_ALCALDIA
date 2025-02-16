package com.prototipo.application.modelDto;

import lombok.*;

import java.math.BigDecimal;

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
