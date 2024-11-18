package com.prototipo.infrastructure.impl;

import com.prototipo.application.modelDto.CargoDto;
import com.prototipo.application.modelDto.CredencialDto;
import com.prototipo.application.port.CargoAbstract;
import com.prototipo.application.port.CredencialAbstract;
import com.prototipo.infrastructure.persistence.db.entity.CargoEntity;
import com.prototipo.infrastructure.persistence.db.entity.CredencialEntity;
import com.prototipo.infrastructure.persistence.db.repository.CargoRepository;
import com.prototipo.infrastructure.persistence.db.repository.CredencialRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CargoImpl implements CargoAbstract {

    @Autowired
    private CargoRepository cargoRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<CargoDto> findAllCargos() {
        List<CargoEntity> list = cargoRepository.findAll();
        return list.stream()
                .map(x -> modelMapper.map(x, CargoDto.class))
                .toList();
    }
}
