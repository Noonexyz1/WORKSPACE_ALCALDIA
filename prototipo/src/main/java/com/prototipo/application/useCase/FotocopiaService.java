package com.prototipo.application.useCase;

import com.prototipo.domain.enums.EstadoByResponsableEnum;
import com.prototipo.domain.model.*;

public interface FotocopiaService {
    UsuarioDomain crearUsuario(UsuarioDomain nuevoUsuario);
    UsuarioDomain editarUsuario(UsuarioDomain usuarioEditado);
    void eliminarUsuario(Long idUsuario);
    void subirArchivoPdf();
    EstadoByResponsableEnum enviarNotificacionEstado();
    OperadorDomain asignarOperador();
    void guardarRegistroInsumosUtilizado(InsumoDomain insumoUtilizado);
    void generarAlertaInsumosBajos();
    ReporteDomain generarReporte();
    SolicitudDomain enviarNotificacionEstadoSoli();
}
