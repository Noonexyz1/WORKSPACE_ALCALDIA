package com.prototipo.infrastructure.persistence.db.adapter;

import com.prototipo.application.port.out.ServicioFotocopiaAbstract;
import com.prototipo.domain.model.ServicioFotocopia;
import com.prototipo.infrastructure.persistence.db.entity.ServicioFotocopiaEntity;
import com.prototipo.infrastructure.persistence.db.repository.ServicioFotocopiaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServicioFotocopiaImpl implements ServicioFotocopiaAbstract {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ServicioFotocopiaRepository servicioFotocopiaRepository;

    @Override
    public ServicioFotocopia findServicioFotocopia(ServicioFotocopia fkServicioFotocopia) {
        ServicioFotocopiaEntity serFotoEntity = servicioFotocopiaRepository
                .findByAnverColorTam(
                        fkServicioFotocopia.getAnverRever(),
                        fkServicioFotocopia.getColor(),
                        fkServicioFotocopia.getTamano()
                );

        return modelMapper.map(serFotoEntity, ServicioFotocopia.class);
    }
}
