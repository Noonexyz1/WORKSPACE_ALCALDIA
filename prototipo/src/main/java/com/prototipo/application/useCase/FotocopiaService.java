package com.prototipo.application.useCase;

import com.prototipo.domain.enums.EstadoByResponsableEnum;
import com.prototipo.domain.model.*;

public interface FotocopiaService {
    void creaUsuario(Usuario userSoli, Long rolId, Long idUni);
    Usuario editarUsuario(Usuario usuarioEditado);
    void eliminarUsuario(Long idUsuario);
    void subirArchivoPdf();
    EstadoByResponsableEnum enviarNotificacionEstado();
    void guardarRegistroInsumosUtilizado(Insumo insumoUtilizado);
    void generarAlertaInsumosBajos();
    Solicitud enviarNotificacionEstadoSoli();
}
