package com.prototipo.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperadorUnidadDomain {
    private Long id;
    private Boolean isActive;
    private UsuarioDomain fkUsuario;
    private UnidadDomain fkUnidad;
}
