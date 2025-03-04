package com.prototipo.application.port.out;

import com.prototipo.application.pager.PaginableIn;
import com.prototipo.application.pager.PaginableOut;
import com.prototipo.domain.model.Finalizacion;

public interface FinalizacionAbstract {
    PaginableOut<Finalizacion> listaDeFinalizacionesAbstractPageByIdSoli(PaginableIn paginableIn);
    PaginableOut<Finalizacion> listaDeFinalizacionesAbstractPageByIdResponsable(PaginableIn paginableIn);
    void guardarFinalizacionAbs(Finalizacion finalizacion);
}
