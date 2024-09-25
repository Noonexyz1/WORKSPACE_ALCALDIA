package com.prototipo.infrastructure.impl;

import com.prototipo.application.modelDto.OperacionDto;
import com.prototipo.application.port.OperacionAbstract;
import com.prototipo.infrastructure.persistence.db.entity.Operacion;
import com.prototipo.infrastructure.persistence.db.repository.OperacionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OperacionImpl implements OperacionAbstract {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private OperacionRepository operacionRepository;

    @Override
    public OperacionDto findOperacionByIdSoliAbstract(Long id) {
        Operacion operacion = operacionRepository.findById(id).orElseThrow();
        return modelMapper.map(operacion, OperacionDto.class);
    }

    @Override
    public OperacionDto guardarOperacion(OperacionDto operacionDto) {
        Operacion operacionToSave = modelMapper.map(operacionDto, Operacion.class);
        Operacion operacion = operacionRepository.save(operacionToSave);
        OperacionDto operacionDtoResp = modelMapper.map(operacion, OperacionDto.class);
        return operacionDtoResp;
    }
}
