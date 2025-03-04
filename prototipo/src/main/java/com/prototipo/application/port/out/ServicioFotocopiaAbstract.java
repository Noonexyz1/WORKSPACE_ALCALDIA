package com.prototipo.application.port.out;

import com.prototipo.application.modelDto.ServicioFotocopiaDto;

public interface ServicioFotocopiaAbstract {
    ServicioFotocopiaDto findServicioFotocopia(ServicioFotocopiaDto fkServicioFotocopia);
}
