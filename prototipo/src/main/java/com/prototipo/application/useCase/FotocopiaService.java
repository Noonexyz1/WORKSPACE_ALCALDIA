package com.prototipo.application.useCase;

import com.prototipo.domain.enums.EstadoByResponsableEnum;
import com.prototipo.domain.model.*;

public interface FotocopiaService {
    void crearUsuario(UsuarioDomain nuevoUsuario, Long rolId, Long rolUnidadResp, String direccion);
    UsuarioDomain editarUsuario(UsuarioDomain usuarioEditado, Long rolId);
    void eliminarUsuario(Long idUsuario);
    void subirArchivoPdf();
    EstadoByResponsableEnum enviarNotificacionEstado();
    OperadorDomain asignarOperador();
    void guardarRegistroInsumosUtilizado(InsumoDomain insumoUtilizado);
    void generarAlertaInsumosBajos();
    ReporteDomain generarReporte();
    SolicitudDomain enviarNotificacionEstadoSoli();
}
