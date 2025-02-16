package com.prototipo.infrastructure.impl;

import com.prototipo.application.modelDto.AutorizacionDto;
import com.prototipo.application.port.AutorizacionAbstract;
import com.prototipo.infrastructure.persistence.db.entity.AutorizacionEntity;
import com.prototipo.infrastructure.persistence.db.repository.AutorizacionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AutorizacionImpl implements AutorizacionAbstract {

    @Autowired
    private AutorizacionRepository autorizacionRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AutorizacionDto findAutorizacionByIdSoli(Long idSolicitud) {
        AutorizacionEntity autorizacionEntity = autorizacionRepository
                .buscarAutorizacionByIdSoli(idSolicitud);
        return modelMapper.map(autorizacionEntity, AutorizacionDto.class);
    }
}
