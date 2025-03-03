package com.prototipo.infrastructure.impl;

import com.prototipo.application.modelDto.ServicioFotocopiaDto;
import com.prototipo.application.port.ServicioFotocopiaAbstract;
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
    public ServicioFotocopiaDto findServicioFotocopia(ServicioFotocopiaDto fkServicioFotocopia) {
        ServicioFotocopiaEntity serFotoEntity = servicioFotocopiaRepository
                .findByAnverColorTam(
                        fkServicioFotocopia.getAnverRever(),
                        fkServicioFotocopia.getColor(),
                        fkServicioFotocopia.getTamano()
                );

        return modelMapper.map(serFotoEntity, ServicioFotocopiaDto.class);
    }
}
