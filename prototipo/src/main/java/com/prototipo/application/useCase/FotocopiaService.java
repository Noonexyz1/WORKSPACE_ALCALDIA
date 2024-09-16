package com.prototipo.application.useCase;

import com.prototipo.domain.enums.Notificacion;
import com.prototipo.domain.model.*;

public interface FotocopiaService {
    Usuario crearUsuario(Usuario nuevoUsuario);
    Usuario editarUsuario(Usuario usuarioEditado);
    void eliminarUsuario(Long idUsuario);
    void subirArchivoPdf();
    Notificacion enviarNotificacionEstado();
    Operador asignarOperador();
    void guardarRegistroInsumosUtilizador(Insumo insumoUtilizado);
    void generarAlertaInsumosBajos();
    Reporte generarReporte();
    Solicitud enviarNotificacionEstadoSoli();
}
