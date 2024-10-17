package com.prototipo.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioUnidad {
    private Long id;
    private Boolean isActive;
    private Usuario fkUsuario;
    private Unidad fkUnidad;
    private Rol fkRol;
}
