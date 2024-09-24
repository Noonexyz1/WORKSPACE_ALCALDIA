package com.prototipo.application.port;

import com.prototipo.application.modelDto.OperacionDto;

public interface OperacionAbstract {
    OperacionDto findOperacionByIdSoliAbstract(Long id);
}
