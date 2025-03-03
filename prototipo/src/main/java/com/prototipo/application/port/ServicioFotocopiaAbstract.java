package com.prototipo.application.port;

import com.prototipo.application.modelDto.ServicioFotocopiaDto;

public interface ServicioFotocopiaAbstract {
    ServicioFotocopiaDto findServicioFotocopia(ServicioFotocopiaDto fkServicioFotocopia);
}
