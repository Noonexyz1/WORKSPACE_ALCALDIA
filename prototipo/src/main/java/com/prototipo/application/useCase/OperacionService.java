package com.prototipo.application.useCase;

import com.prototipo.domain.model.OperacionDomain;

public interface OperacionService {
    OperacionDomain findOperacionByIdSoliService(Long id);
}
