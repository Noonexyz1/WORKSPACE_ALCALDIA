package com.prototipo.infrastructure.impl;

import com.prototipo.application.modelDto.OperacionDto;
import com.prototipo.application.port.OperacionAbstract;
import com.prototipo.infrastructure.persistence.db.entity.OperacionEntity;
import com.prototipo.infrastructure.persistence.db.repository.OperacionRepository;
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
    @Autowired
    private OperacionRepository operacionRepository;

    @Override
    public OperacionDto findOperacionByIdSoliAbstract(Long id) {
        OperacionEntity operacionEntity = operacionRepository.findById(id).orElse(null);
        if (operacionEntity == null) {
            return null;
        }
        return modelMapper.map(operacionEntity, OperacionDto.class);
    }

    @Override
    public OperacionDto guardarOperacion(OperacionDto operacionDto) {
        OperacionEntity operacionEntityToSave = modelMapper.map(operacionDto, OperacionEntity.class);
        OperacionEntity operacionEntity = operacionRepository.save(operacionEntityToSave);
        OperacionDto operacionDtoResp = modelMapper.map(operacionEntity, OperacionDto.class);
        return operacionDtoResp;
    }

    @Override
    public List<OperacionDto> listaDeOperaciones() {
        return operacionRepository.findAll()
                .stream()
                .map(x -> modelMapper.map(x, OperacionDto.class))
                .toList();
    }

    @Override
    public List<OperacionDto> findOperacionByIdOperadorPendientesAbstract(Long idOperador, Long page, Long size) {
        //TODO, probar este paginable
        Pageable pageable = PageRequest.of(page.intValue(), size.intValue());
        Page<OperacionEntity> listOpe = operacionRepository
                .findOperacionesByOperadorAndEstadoPendiente(idOperador, pageable);
        return listOpe.stream().map(x -> modelMapper.map(x, OperacionDto.class)).toList();
    }

    @Override
    public List<OperacionDto> findOperacionByIdOperadorIniciadasAbstract(Long idOperador, Long page, Long size) {
        //TODO, probar este paginable
        Pageable pageable = PageRequest.of(page.intValue(), size.intValue());
        Page<OperacionEntity> listOpe = operacionRepository
                .findOperacionesByOperadorAndEstadoIniciado(idOperador, pageable);
        return listOpe.stream().map(x -> modelMapper.map(x, OperacionDto.class)).toList();
    }

    @Override
    public List<OperacionDto> findOperacionByIdOperadorCompletadasAbstract(Long idOperador, Long page, Long size) {
        //TODO, probar este paginable
        Pageable pageable = PageRequest.of(page.intValue(), size.intValue());
        Page<OperacionEntity> listOpe = operacionRepository
                .findOperacionesByOperadorAndEstadoCompletado(idOperador, pageable);

        return listOpe.stream().map(x -> modelMapper.map(x, OperacionDto.class)).toList();
    }
}
