package com.prototipo.application.port;

import com.prototipo.application.modelDto.RolDto;

import java.util.List;

public interface RolAbstract {
    RolDto encontrarRolPorId(Long idRol);
    List<RolDto> listarRoles();
}
