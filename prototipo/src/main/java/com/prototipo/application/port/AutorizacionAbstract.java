package com.prototipo.application.port;

import com.prototipo.application.modelDto.AutorizacionDto;
import com.prototipo.application.pager.PaginableIn;
import com.prototipo.application.pager.PaginableOut;

public interface AutorizacionAbstract {
    AutorizacionDto findAutorizacionByIdSoli(Long idSolicitud);
    AutorizacionDto buscarAutorizacionByIdAbs(Long idAutorizacion);
    PaginableOut<AutorizacionDto> listaDeSoliAutorizadasAbstractPageByIdSoli(PaginableIn paginableIn);
    PaginableOut<AutorizacionDto> listaDeSoliAutorizadasAbstractPageByIdResponsable(PaginableIn paginableIn);
    void guardarAutorizacionAbs(AutorizacionDto autorizacionDto);
}
