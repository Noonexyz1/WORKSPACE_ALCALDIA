package com.prototipo.application.port;

import com.prototipo.application.modelDto.FinalizacionDto;
import com.prototipo.application.pager.PaginableIn;
import com.prototipo.application.pager.PaginableOut;

public interface FinalizacionAbstract {
    PaginableOut<FinalizacionDto> listaDeFinalizacionesAbstractPageByIdSoli(PaginableIn paginableIn);
    PaginableOut<FinalizacionDto> listaDeFinalizacionesAbstractPageByIdResponsable(PaginableIn paginableIn);
    void guardarFinalizacionAbs(FinalizacionDto finalizacionDto);
}
