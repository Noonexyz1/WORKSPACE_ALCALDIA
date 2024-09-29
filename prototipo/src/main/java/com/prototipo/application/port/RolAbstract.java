package com.prototipo.application.port;

import com.prototipo.application.modelDto.RolDto;

public interface RolAbstract {
    RolDto encontrarRolPorId(Long idRol);
}
