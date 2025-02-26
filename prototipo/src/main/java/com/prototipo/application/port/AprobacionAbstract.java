package com.prototipo.application.port;

import com.prototipo.application.modelDto.AutorizacionDto;
import com.prototipo.application.modelDto.FinalizacionDto;
import com.prototipo.application.modelDto.SolicitudDto;
import com.prototipo.application.pager.PaginableIn;
import com.prototipo.application.pager.PaginableOut;

public interface AprobacionAbstract {
    PaginableOut<SolicitudDto> listaDeSolicitudesPendientesAbstractPage(PaginableIn paginableIn);
    PaginableOut<FinalizacionDto> listaDeAprobacionesFinalizadasAbstractPage(PaginableIn paginableIn);
    PaginableOut<AutorizacionDto> listaDeSoliAutorizadasAbstractPage(PaginableIn paginableIn);
    PaginableOut<SolicitudDto> listaDeSolicitudesPendientesAbstractPageByIdSoli(PaginableIn paginableIn);

    PaginableOut<AutorizacionDto> listaDeSoliAutorizadasAbstractPageByIdSoli(PaginableIn paginableIn);

    PaginableOut<FinalizacionDto> listaDeAprobacionesFinalizadasAbstractPageByIdSoli(PaginableIn paginableIn);
}
