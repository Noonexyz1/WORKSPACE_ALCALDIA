package com.prototipo.application.port.out;

import com.prototipo.application.pager.PaginableIn;
import com.prototipo.application.pager.PaginableOut;
import com.prototipo.domain.model.Autorizacion;

public interface AutorizacionAbstract {
    Autorizacion findAutorizacionByIdSoli(Long idSolicitud);
    Autorizacion buscarAutorizacionByIdAbs(Long idAutorizacion);
    void guardarAutorizacionAbs(Autorizacion autorizacion);
    PaginableOut<Autorizacion> listaDeSoliAutorizadasAbstractPageByIdSoli(PaginableIn paginableIn);
    PaginableOut<Autorizacion> listaDeSoliAutorizadasAbstractPageByIdResponsable(PaginableIn paginableIn);
}
