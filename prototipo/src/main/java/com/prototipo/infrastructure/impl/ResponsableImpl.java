package com.prototipo.infrastructure.impl;

import com.prototipo.application.modelDto.ResponsableDto;
import com.prototipo.application.port.ResponsableAbstract;
import com.prototipo.infrastructure.persistence.db.entity.Responsable;
import com.prototipo.infrastructure.persistence.db.repository.ResponsableRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResponsableImpl implements ResponsableAbstract {

    @Autowired
    private ResponsableRepository responsableRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void aprobarSolicitudAbstract(Long idSolicitud) {
        //TODO
    }

    @Override
    public ResponsableDto guardarResponsable(ResponsableDto respDto) {
        Responsable newResp = modelMapper.map(respDto, Responsable.class);
        Responsable respResp = responsableRepository.save(newResp);
        return modelMapper.map(respDto, ResponsableDto.class);
    }
}
