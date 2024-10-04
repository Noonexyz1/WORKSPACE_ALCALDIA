package com.prototipo.application.port;

import com.prototipo.application.modelDto.OperacionDto;

import java.util.List;

public interface OperacionAbstract {
    OperacionDto findOperacionByIdSoliAbstract(Long id);
    OperacionDto guardarOperacion(OperacionDto operacionDto);
    List<OperacionDto> listaDeOperaciones();

    //TODO, metodo para probar
    List<OperacionDto> findOperacionByIdOperadorAbstract(Long idOperador, String estadoOpe);
}
