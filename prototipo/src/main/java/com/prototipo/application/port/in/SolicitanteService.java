package com.prototipo.application.port.in;

import com.prototipo.application.model.PaginableIn;
import com.prototipo.application.model.PaginableOut;
import com.prototipo.domain.model.*;

import java.io.IOException;
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

    void generarOrdenDeFotocopiaPDF(Long idSolicitud);
    void generarComunicacionInternaPDF(Long idSolicitud);
    void generarSolicitudDeFotocopiaPDF(Long idSolicitud);

    byte[] descargaSolicitudDeFotocopiaPDF(Long idSolicitud) throws IOException;
    byte[] descargarOrdenDeFotocopiaPDF(Long idSolicitud) throws IOException;
    byte[] descargarComunicacionInternaPDF(Long idSolicitud) throws IOException;

    List<DocumentoRetiro> listDocumentoRetirar(Long idSolicitud);

    void guardarListaDocuRetiros(List<DocumentoRetiro> listDocumentoRetiro);
}
