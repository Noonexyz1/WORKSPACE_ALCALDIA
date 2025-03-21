package com.prototipo.infrastructure.persistence.db.adapter;

import com.prototipo.application.port.out.persistence.CargoAbstract;
import com.prototipo.domain.model.Cargo;
import com.prototipo.infrastructure.persistence.db.entity.CargoEntity;
import com.prototipo.infrastructure.persistence.db.repository.CargoRepository;
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
    public List<Cargo> findAllCargos() {
        List<CargoEntity> list = cargoRepository.findAll();
        return list.stream()
                .map(x -> modelMapper.map(x, Cargo.class))
                .toList();
    }
}
