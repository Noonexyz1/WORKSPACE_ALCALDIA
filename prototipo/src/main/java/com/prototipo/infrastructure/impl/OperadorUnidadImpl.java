package com.prototipo.infrastructure.impl;

import com.prototipo.application.modelDto.OperadorUnidadDto;
import com.prototipo.application.port.OperadorUnidadAbstract;
import com.prototipo.infrastructure.persistence.db.entity.OperadorUnidad;
import com.prototipo.infrastructure.persistence.db.repository.OperadorUnidadRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OperadorUnidadImpl implements OperadorUnidadAbstract {

    @Autowired
    private OperadorUnidadRepository operadorUnidadRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public OperadorUnidadDto guardarOperadorUnidadAbstract(OperadorUnidadDto operadorUnidadDto) {
        OperadorUnidad operadorUnidad = modelMapper.map(operadorUnidadDto, OperadorUnidad.class);
        OperadorUnidad operadorUnidadResp = operadorUnidadRepository.save(operadorUnidad);
        return modelMapper.map(operadorUnidadResp, OperadorUnidadDto.class);
    }

    @Override
    public List<OperadorUnidadDto> encontrarOperadorUnidadById(Long idOperador) {
        List<OperadorUnidad> listOperadorUnidad = operadorUnidadRepository.findByFkUsuario_Id(idOperador);
        return listOperadorUnidad.stream()
                .map(x -> modelMapper.map(x, OperadorUnidadDto.class))
                .toList();
    }
}
