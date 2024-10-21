package com.prototipo.application.useCase;

import com.prototipo.domain.enums.EstadoByResponsableEnum;
import com.prototipo.domain.model.*;

import java.util.List;

public interface FotocopiaService {
    void creaUsuario(Usuario userSoli, Long rolId, Long idUni);
    UsuarioUnidad editarUsuarioUnidad(UsuarioUnidad usuarioEditado);
    void eliminarUsuario(Long idUsuario);
    void subirArchivoPdf();
    EstadoByResponsableEnum enviarNotificacionEstado();
    void guardarRegistroInsumosUtilizado(Insumo insumoUtilizado);
    void generarAlertaInsumosBajos();
    Solicitud enviarNotificacionEstadoSoli();
    List<Rol> listarRolesService();
    List<Unidad> listarUnidadesService();
    void cambiarPass(Credencial credencial, String newPass);
}
