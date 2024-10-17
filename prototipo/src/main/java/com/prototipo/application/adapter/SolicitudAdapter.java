package com.prototipo.application.adapter;

import com.prototipo.application.mapper.MapperApplicationAbstract;
import com.prototipo.application.modelDto.*;
import com.prototipo.application.port.AprobacionAbstract;
import com.prototipo.application.port.SolicitudAbstract;
import com.prototipo.application.useCase.SolicitudService;
import com.prototipo.domain.enums.EstadoByResponsableEnum;
import com.prototipo.domain.model.ArchivoPdf;
import com.prototipo.domain.model.Solicitud;

import java.util.List;

public class SolicitudAdapter implements SolicitudService {

    private SolicitudAbstract solicitudAbstract;
    private MapperApplicationAbstract mapperApplicationAbstract;
    private AprobacionAbstract aprobacionAbstract;

    public SolicitudAdapter(SolicitudAbstract solicitudAbstract,
                            MapperApplicationAbstract mapperApplicationAbstract,
                            AprobacionAbstract aprobacionAbstract) {

        this.solicitudAbstract = solicitudAbstract;
        this.mapperApplicationAbstract = mapperApplicationAbstract;
        this.aprobacionAbstract = aprobacionAbstract;
    }

    @Override
    public void solicitarFotocopiarService(Solicitud solicitudDomain, List<ArchivoPdf> listPdf) {
        //TODO, Puede que esta operacion de registro de archivos tarde un poco
        //LLaves necesarias para las tablas
        SolicitudDto solicitudDto = mapperApplicationAbstract.mapearAbstract(solicitudDomain, SolicitudDto.class);

        //Aqui hacemos la percistencia
        SolicitudDto solicitudDtoResp = solicitudAbstract.solicitarFotocopiarAbstract(solicitudDto);
        Solicitud solicitudResp = mapperApplicationAbstract.mapearAbstract(solicitudDtoResp, Solicitud.class);

        listPdf.forEach(x -> {
            x.setFkSolicitud(solicitudResp);
            guardarPdfDeLaSolicitudAbstract(x);
        });

        //Hacemos la persistencia de las solicitudes en la tabla de Aprobacion sin
        //el responsable que lo aprueba
        AprobacionDto aprobacionDto = AprobacionDto.builder()
                //El responsable que lo ha aprobado
                //.fkResponsable()
                .fkSolicitud(solicitudDtoResp)
                .estadoByResponsable(EstadoByResponsableEnum.PENDIENTE.getNombre())
                .estadoCambio(false)
                .build();
        aprobacionAbstract.guardarAprobacionAbstract(aprobacionDto);
    }

    @Override
    public void guardarPdfDeLaSolicitudAbstract(ArchivoPdf archivoPdfDomain) {
        ArchivoPdfDto archivoPdfDto = mapperApplicationAbstract.mapearAbstract(archivoPdfDomain, ArchivoPdfDto.class);
        solicitudAbstract.guardarPdfDeLaSolicitudAbstract(archivoPdfDto);
    }

    @Override
    public List<Solicitud> getListaSolicitudesService(Long idUsuario, Long page, Long size) {
        List<SolicitudDto> listSoli = solicitudAbstract.getListaSolicitudesAbstract(idUsuario, page, size);
        //Quiero filtar las solicitudes segun el Usuario que lo esta pidiendo
        return listSoli.stream()
                .map(x -> mapperApplicationAbstract.mapearAbstract(x, Solicitud.class))
                .toList();
    }

    @Override
    public void guardarSolicitudService(Solicitud solicitudDomain) {
        //TODO
    }

    @Override
    public Solicitud buscarSolicitudService(Long id) {
        //TODO
        return null;
    }
}
