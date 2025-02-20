package com.prototipo.application.port;

import com.prototipo.application.modelDto.AutorizacionDto;
import com.prototipo.application.modelDto.FinalizacionDto;
import com.prototipo.application.modelDto.SolicitudDto;

import java.util.List;

public interface AprobacionAbstract {
    List<SolicitudDto> listaDeSolicitudesPendientesAbstractPage(Long idSupervisor, Long page, Long size, String byColumName);
    List<FinalizacionDto> listaDeAprobacionesFinalizadasAbstractPage(Long idSupervisor, Long page, Long size, String byColumName);
    List<AutorizacionDto> listaDeSoliAutorizadasAbstractPage(Long idSupervisor, Long page, Long size, String byColumName);
}
