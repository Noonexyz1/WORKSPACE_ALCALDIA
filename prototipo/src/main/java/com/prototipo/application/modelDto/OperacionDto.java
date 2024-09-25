package com.prototipo.application.modelDto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperacionDto {
    private Long id;
    private String estadoByOperador;
    private AprobacionDto fkAprobacion;
    private UsuarioDto fkOperador;
}
