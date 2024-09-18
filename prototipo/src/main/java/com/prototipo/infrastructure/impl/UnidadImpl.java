package com.prototipo.infrastructure.impl;

import com.prototipo.application.modelDto.UnidadDto;
import com.prototipo.application.port.UnidadAbstract;
import com.prototipo.infrastructure.persistence.db.entity.Unidad;
import com.prototipo.infrastructure.persistence.db.repository.UnidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UnidadImpl implements UnidadAbstract {

    @Autowired
    private UnidadRepository unidadRepository;

    @Override
    public List<UnidadDto> listaDeUnidadesAbstract() {
        List<Unidad> unidadList = unidadRepository.findAll();

        return unidadList.stream().map(x ->
                UnidadDto.builder()
                        .id(x.getId())
                        .nombre(x.getNombre())
                        .direccion(x.getDireccion())
                        .build()
        ).toList();

    }
}
