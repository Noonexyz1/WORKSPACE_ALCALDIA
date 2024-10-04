package com.prototipo.application.port;

import com.prototipo.application.modelDto.OperadorUnidadDto;

import java.util.List;

public interface OperadorUnidadAbstract {
    OperadorUnidadDto guardarOperadorUnidadAbstract(OperadorUnidadDto operadorUnidadDto);
    List<OperadorUnidadDto> encontrarOperadorUnidadById(Long idOperador);
}
