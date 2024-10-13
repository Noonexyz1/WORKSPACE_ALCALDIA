package com.prototipo.application.port;

import com.prototipo.application.modelDto.AprobacionDto;

import java.util.List;

public interface AprobacionAbstract {
    AprobacionDto guardarAprobacionAbstract(AprobacionDto aprobacionDto);
    AprobacionDto findAprovacionByIdSoliAbstract(Long id);
    List<AprobacionDto> listaDeAprobacionesPendientesAbstractPage(Long idSupervisor, Long page, Long size, String byColumName);
    List<AprobacionDto> listaDeAprobacionesAprobadasAbstractPage(Long idSupervisor, Long page, Long size, String byColumName);
    List<AprobacionDto> listaDeAprobacionesRechazadasAbstractPage(Long idSupervisor, Long page, Long size, String byColumName);
    List<AprobacionDto> listaDeSolicitudesAbstract();
    List<AprobacionDto> listaDeSolicitudesByFkSoliAbstract(Long idSoli);
    List<AprobacionDto> listaDeSolicitudesByUnidad(String nombreUnidad);
}
