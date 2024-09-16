package com.prototipo.application.adapter;

import com.prototipo.application.useCase.OperadorService;
import com.prototipo.application.useCase.SolicitudPersist;
import com.prototipo.domain.enums.EstadoSolicitud;
import com.prototipo.domain.model.Solicitud;

import java.util.List;

public class OperadorAdapter implements OperadorService {

    private SolicitudPersist solicitudPersist;



    @Override
    public List<Solicitud> verSolicitudes() {
        return this.solicitudPersist.getSolicitudesOperador();
    }

    @Override
    public boolean cambiarEstadoDeSolicitud(Solicitud solicitud, EstadoSolicitud nuevoEstado) {
        Solicitud soli = Solicitud.builder()
                .id(solicitud.getId())
                .nroDeCopias(solicitud.getNroDeCopias())
                .nroDePaginas(solicitud.getNroDePaginas())
                .tipoDeDocumento(solicitud.getTipoDeDocumento())
                .build();


        return false;
    }
}
