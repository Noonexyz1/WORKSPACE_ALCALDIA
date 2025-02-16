package com.prototipo.application.port;

import com.prototipo.application.modelDto.AprobacionDto;
import com.prototipo.application.modelDto.AutorizacionDto;
import com.prototipo.application.modelDto.FinalizacionDto;
import com.prototipo.application.modelDto.SolicitudDto;

import java.util.List;

public interface AprobacionAbstract {
    AprobacionDto guardarAprobacionAbstract(AprobacionDto aprobacionDto);
    AprobacionDto findAprovacionByIdSoliAbstract(Long id);
    List<SolicitudDto> listaDeSolicitudesPendientesAbstractPage(Long idSupervisor, Long page, Long size, String byColumName);
    List<AprobacionDto> listaDeAprobacionesAprobadasAbstractPage(Long idSupervisor, Long page, Long size, String byColumName);
    List<AprobacionDto> listaDeAprobacionesRechazadasAbstractPage(Long idSupervisor, Long page, Long size, String byColumName);
    List<FinalizacionDto> listaDeAprobacionesFinalizadasAbstractPage(Long idSupervisor, Long page, Long size, String byColumName);
    List<AutorizacionDto> listaDeSoliAutorizadasAbstractPage(Long idSupervisor, Long page, Long size, String byColumName);
    AutorizacionDto findAutorizacionByIdAbstract(Long idAutorizacion);
    void guardarAutorizacionAbstract(AutorizacionDto autorizacionDto);
}
