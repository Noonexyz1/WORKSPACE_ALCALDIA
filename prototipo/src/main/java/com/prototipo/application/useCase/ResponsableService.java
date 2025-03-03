package com.prototipo.application.useCase;

import com.prototipo.application.pager.PaginableIn;
import com.prototipo.application.pager.PaginableOut;
import com.prototipo.domain.model.*;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;
import java.util.List;

public interface ResponsableService {
    void rechazarSolicitud(Long idAprobacion, Long idResponsable);
    void guardarAutorizacion(Autorizacion autorizacion);
    void guardarFinalizacion(Finalizacion finalizacion);
    Autorizacion obtenerAutorizacion(Long idSolicitud);
    List<NotaDePedido> listaDeNotasDePedido(Long idSolicitud);
    List<Reporte> listaDeReportes(Long idSolicitud);
    List<Fotocopia> listaDeFotocopias(Long idSolicitud);
    PaginableOut<Finalizacion> listaDeSolicitudesFinalizadasByIdSolicitud(PaginableIn paginableIn);
    PaginableOut<Solicitud> listaDeSolicitudesPendientesByIdSolicitud(PaginableIn paginableIn);
    PaginableOut<Autorizacion> listaDeSolicitudesAutorizadasByIdSolicitud(PaginableIn paginableIn);
    PaginableOut<Solicitud> listaDeSolicitudesPendientesByIdResponsable(PaginableIn paginableIn);
    PaginableOut<Autorizacion> listaDeSolicitudesAutorizadasByIdResponsable(PaginableIn paginableIn);
    PaginableOut<Finalizacion> listaDeSolicitudesFinalizadasByIdResponsable(PaginableIn paginableIn);
    void generarNotaPedidoPDF(Long idSolicitud) throws JRException, IOException;
    void generarReportePDF(Long idSolicitud) throws JRException, IOException;
}
