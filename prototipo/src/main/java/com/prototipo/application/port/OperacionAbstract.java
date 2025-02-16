package com.prototipo.application.port;

import com.prototipo.application.modelDto.OperacionDto;

import java.util.List;

public interface OperacionAbstract {
    OperacionDto findOperacionByIdSoliAbstract(Long id);
    OperacionDto guardarOperacion(OperacionDto operacionDto);
    List<OperacionDto> findOperacionByIdOperadorPendientesAbstract(Long idOperador, Long page, Long size);
    List<OperacionDto> findOperacionByIdOperadorIniciadasAbstract(Long idOperador, Long page, Long size);
    List<OperacionDto> findOperacionByIdOperadorCompletadasAbstract(Long idOperador, Long page, Long size);
}
