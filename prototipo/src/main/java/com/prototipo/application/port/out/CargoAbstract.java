package com.prototipo.application.port.out;

import com.prototipo.application.modelDto.CargoDto;

import java.util.List;

public interface CargoAbstract {
    List<CargoDto> findAllCargos();
}
