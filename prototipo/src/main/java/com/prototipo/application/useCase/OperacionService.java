package com.prototipo.application.useCase;

import com.prototipo.domain.model.Operacion;

public interface OperacionService {
    Operacion findOperacionByIdSoliService(Long id);
}
