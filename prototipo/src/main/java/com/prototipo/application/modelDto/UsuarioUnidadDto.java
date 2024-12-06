package com.prototipo.application.modelDto;

import com.prototipo.infrastructure.persistence.db.entity.UsuarioUnidadEntity;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioUnidadDto {
    private Long id;
    private Boolean isActive;
    private UsuarioDto fkUsuario;
    private UnidadDto fkUnidad;
    private RolDto fkRol;
    private CargoDto fkCargo;
    private UsuarioUnidadDto fkResponsable;
    private UsuarioUnidadDto fkDirector;
}
