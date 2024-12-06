package com.prototipo.domain.model;

import com.prototipo.infrastructure.persistence.db.entity.UsuarioUnidadEntity;
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
    private Cargo fkCargo;
    private UsuarioUnidad fkResponsable;
    private UsuarioUnidad fkDirector;
}
