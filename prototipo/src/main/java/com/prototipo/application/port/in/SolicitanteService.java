package com.prototipo.application.port.in;

import com.prototipo.application.pager.PaginableIn;
import com.prototipo.application.pager.PaginableOut;
import com.prototipo.domain.model.*;

//TODO, quitar esta importacion
import net.sf.jasperreports.engine.JRException;

import java.util.List;

public interface SolicitanteService {
    void solicitarFotocopia(Solicitud solicitud, List<Fotocopia> listFotocopia);
    void eliminarSolicitud(Long idSolicitud);
    Solicitud buscarSolicitud(Long idSolicitud);
    List<Fotocopia> listaDeFotocopias(Long idSolicitud);
    List<String> listaDeTamanoPagina();
    List<String> listaDeAnverReverPagina();
    List<String> listaDeColorPagina();
    PaginableOut<Solicitud> listaDeSolicitudes(PaginableIn paginableIn);
    PaginableOut<Solicitud> listaDeAutorizaciones(PaginableIn paginableIn);
    PaginableOut<Solicitud> listaDeFinalizaciones(PaginableIn paginableIn);

    void generarOrdenDeFotocopiaPDF(Long idSolicitud) throws JRException;
    void generarComunicacionInternaPDF(Long idSolicitud) throws JRException;
    void generarSolicitudDeFotocopiaPDF(Long idSolicitud) throws JRException;
}
