package com.prototipo.infrastructure.impl;

import com.prototipo.application.modelDto.OperacionDto;
import com.prototipo.application.port.OperacionAbstract;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OperacionImpl implements OperacionAbstract {

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public OperacionDto findOperacionByIdSoliAbstract(Long id) {

        return null;
    }

    @Override
    public OperacionDto guardarOperacion(OperacionDto operacionDto) {

        return null;
    }

    @Override
    public List<OperacionDto> listaDeOperaciones() {

        return null;
    }

    @Override
    public List<OperacionDto> findOperacionByIdOperadorPendientesAbstract(Long idOperador, Long page, Long size) {

        return null;
    }

    @Override
    public List<OperacionDto> findOperacionByIdOperadorIniciadasAbstract(Long idOperador, Long page, Long size) {

        return null;
    }

    @Override
    public List<OperacionDto> findOperacionByIdOperadorCompletadasAbstract(Long idOperador, Long page, Long size) {

        return null;
    }
}
