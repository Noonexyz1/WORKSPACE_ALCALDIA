package com.prototipo.application.port;

import com.prototipo.application.modelDto.AutorizacionDto;
import com.prototipo.application.modelDto.FinalizacionDto;
import com.prototipo.application.pager.PaginableIn;
import com.prototipo.application.pager.PaginableOut;

public interface AprobacionAbstract {
    //TODO, estos metodos no tienen relacion con esta interfaz
    PaginableOut<FinalizacionDto> listaDeFinalizacionesAbstractPageByIdSoli(PaginableIn paginableIn);
    PaginableOut<FinalizacionDto> listaDeFinalizacionesAbstractPageByIdResponsable(PaginableIn paginableIn);

    PaginableOut<AutorizacionDto> listaDeSoliAutorizadasAbstractPageByIdSoli(PaginableIn paginableIn);
    PaginableOut<AutorizacionDto> listaDeSoliAutorizadasAbstractPageByIdResponsable(PaginableIn paginableIn);
}
