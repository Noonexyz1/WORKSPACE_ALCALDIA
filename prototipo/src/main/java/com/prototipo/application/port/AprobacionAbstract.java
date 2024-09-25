package com.prototipo.application.port;

import com.prototipo.application.modelDto.AprobacionDto;

import java.util.List;

public interface AprobacionAbstract {
    AprobacionDto guardarAprobacionAbstract(AprobacionDto aprobacionDto);
    AprobacionDto findAprovacionByIdSoliAbstract(Long id);
    List<AprobacionDto> listaDeSolicitudesAbstract();
}
