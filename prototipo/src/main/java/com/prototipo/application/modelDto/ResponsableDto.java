package com.prototipo.application.modelDto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponsableDto {
    private Long id;
    private Boolean isActive;
    private UsuarioDto fkUsuario;
    private UnidadDto fkUnidad;
}
