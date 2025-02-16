package com.prototipo.infrastructure.impl;

import com.prototipo.application.modelDto.UnidadDto;
import com.prototipo.application.port.UnidadAbstract;
import com.prototipo.infrastructure.persistence.db.entity.UnidadEntity;
import com.prototipo.infrastructure.persistence.db.repository.UnidadRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UnidadImpl implements UnidadAbstract {

    @Autowired
    private UnidadRepository unidadRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<UnidadDto> listaDeUnidadesAbstract() {
        return unidadRepository.findAll()
                .stream()
                .map(x -> modelMapper.map(x, UnidadDto.class))
                .toList();
    }

    @Override
    public UnidadDto findUnidadPorIdAbstract(Long idUnidad) {
        UnidadEntity unidadEntity = unidadRepository.findById(idUnidad).orElseThrow();
        UnidadDto unidadDto = UnidadDto.builder()
                .id(unidadEntity.getId())
                .nombre(unidadEntity.getNombreUnidad())
                .direccion(unidadEntity.getDireccion())
                .build();
        return unidadDto;
    }
}
