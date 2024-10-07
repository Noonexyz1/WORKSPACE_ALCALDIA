package com.prototipo.application.useCase;

import com.prototipo.domain.enums.EstadoByResponsableEnum;
import com.prototipo.domain.model.*;

public interface FotocopiaService {
    void creaUsuarioSolicitante(UsuarioDomain userSoli, Long rolId);
    void creaUsuarioResponsable(UsuarioDomain userRespon, Long rolId, Long idUniRespon);
    void creaUsuarioOperador(UsuarioDomain userOpe, Long rolId, String direccionPiso);
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
