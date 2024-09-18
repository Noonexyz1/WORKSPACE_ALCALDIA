package com.prototipo.application.adapter;

import com.prototipo.application.modelDto.SolicitudDto;
import com.prototipo.application.port.SolicitudAbstract;
import com.prototipo.application.useCase.SolicitudService;
import com.prototipo.domain.model.Solicitud;

import java.util.List;

public class SolicitudAdapter implements SolicitudService {

    private SolicitudAbstract solicitudAbstract;

    public SolicitudAdapter(SolicitudAbstract solicitudAbstract){
        this.solicitudAbstract = solicitudAbstract;
    }


    @Override
    public void solicitarFotocopiarService(Solicitud solicitud) {
        //TODO Mapeado de instancias
        SolicitudDto solicitudDto = SolicitudDto.builder()
                .nroDeCopias(solicitud.getNroDeCopias())
                .tipoDeDocumento(solicitud.getTipoDeDocumento())
                .nroDePaginas(solicitud.getNroDePaginas())
                .build();

        solicitudAbstract.solicitarFotocopiarAbstract(solicitudDto);
    }

    @Override
    public List<Solicitud> getListaSolicitudesService() {
        //Hacer los mapeos correspondientes
        List<SolicitudDto> solicitudDtoList = solicitudAbstract.getListaSolicitudesAbstract();
        return List.of();
    }

    @Override
    public List<Solicitud> getSolicitudesOperadorService() {
        //Hacer los mapeos correspondientes
        List<SolicitudDto> solicitudDtoList = solicitudAbstract.getSolicitudesOperadorAbstract();
        return List.of();
    }
}
