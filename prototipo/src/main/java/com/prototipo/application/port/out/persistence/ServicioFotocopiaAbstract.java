package com.prototipo.application.port.out.persistence;

import com.prototipo.domain.model.ServicioFotocopia;

public interface ServicioFotocopiaAbstract {
    ServicioFotocopia findServicioFotocopia(ServicioFotocopia fkServicioFotocopia);
}
