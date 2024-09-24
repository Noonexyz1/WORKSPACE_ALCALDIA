package com.prototipo.infrastructure.impl;

import com.prototipo.application.mapper.MapperApplicationAbstract;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MapperApplicationImpl implements MapperApplicationAbstract {

    private final ModelMapper modelMapper;

    @Autowired
    public MapperApplicationImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public <T> T mapearAbstract(Object objectFrom, Class<T> targetClass) {
        return modelMapper.map(objectFrom, targetClass);
    }
}
