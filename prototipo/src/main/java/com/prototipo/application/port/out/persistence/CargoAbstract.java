package com.prototipo.application.port.out.persistence;

import com.prototipo.domain.model.Cargo;

import java.util.List;

public interface CargoAbstract {
    List<Cargo> findAllCargos();
}
