package com.prototipo.application.port;

import com.prototipo.application.modelDto.AutorizacionDto;

public interface AutorizacionAbstract {
    AutorizacionDto findAutorizacionByIdSoli(Long idSolicitud);
    AutorizacionDto buscarAutorizacionByIdAbs(Long idAutorizacion);
    void guardarAutorizacionAbs(AutorizacionDto autorizacionDto);
}
