package com.prototipo.application.impl;

import com.prototipo.application.port.FotocopiaService;
import com.prototipo.domain.enums.Notificacion;
import com.prototipo.domain.model.*;

public class FotocopiaImpl implements FotocopiaService {




    @Override
    public Usuario crearUsuario(Usuario nuevoUsuario) {
        return null;
    }

    @Override
    public Usuario editarUsuario(Usuario usuarioEditado) {
        return null;
    }

    @Override
    public void eliminarUsuario(Long idUsuario) {

    }

    @Override
    public void subirArchivoPdf() {

    }

    @Override
    public Notificacion enviarNotificacionEstado() {
        return null;
    }

    @Override
    public Operador asignarOperador() {
        return null;
    }

    @Override
    public void guardarRegistroInsumosUtilizador(Insumo insumoUtilizado) {

    }

    @Override
    public void generarAlertaInsumosBajos() {

    }

    @Override
    public Reporte generarReporte() {
        return null;
    }

    @Override
    public Solicitud enviarNotificacionEstadoSoli() {
        return null;
    }
}
