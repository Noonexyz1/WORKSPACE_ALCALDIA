package com.prototipo.application.adapter;

import com.prototipo.application.mapper.MapperApplicationAbstract;
import com.prototipo.application.modelDto.*;
import com.prototipo.application.port.AprobacionAbstract;
import com.prototipo.application.port.SolicitudAbstract;
import com.prototipo.application.useCase.SolicitudService;
import com.prototipo.domain.enums.EstadoByResponsableEnum;
import com.prototipo.domain.model.ArchivoPdfDomain;
import com.prototipo.domain.model.SolicitudDomain;

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
    public void solicitarFotocopiarService(SolicitudDomain solicitudDomain, List<ArchivoPdfDomain> listPdf) {
        //Mapeo de instancias
        UnidadDto unidadDto = mapperApplicationAbstract.mapearAbstract(solicitudDomain.getFkUnidad(), UnidadDto.class);

        //LLaves necesarias para las tablas
        SolicitudDto solicitudDto = mapperApplicationAbstract.mapearAbstract(solicitudDomain, SolicitudDto.class);
        UsuarioDto usuarioDto = mapperApplicationAbstract.mapearAbstract(solicitudDomain.getFkSolicitante(), UsuarioDto.class);

        //Aqui hacemos la percistencia
        SolicitudDto solicitudDtoResp = solicitudAbstract.solicitarFotocopiarAbstract(solicitudDto);
        SolicitudDomain solicitudDomainResp = mapperApplicationAbstract.mapearAbstract(solicitudDtoResp, SolicitudDomain.class);

        //Hacemos la persistencia de los archivos
        listPdf.forEach(x -> {
            x.setFkSolicitud(solicitudDomainResp);
            guardarPdfDeLaSolicitudAbstract(x);
        });

        //Hacemos la persistencia de las solicitudes en la tabla de Aprobacion sin
        //el responsable que lo aprueba
        AprobacionDto aprobacionDto = AprobacionDto.builder()
                //El responsable que lo ha aprobado
                //.fkResponsable()
                .fkSolicitud(solicitudDtoResp)
                .estadoByResponsable(EstadoByResponsableEnum.PENDIENTE.getNombre())
                .build();
        aprobacionAbstract.guardarAprobacionAbstract(aprobacionDto);
    }

    @Override
    public void guardarPdfDeLaSolicitudAbstract(ArchivoPdfDomain archivoPdfDomain) {
        ArchivoPdfDto archivoPdfDto = mapperApplicationAbstract.mapearAbstract(archivoPdfDomain, ArchivoPdfDto.class);
        solicitudAbstract.guardarPdfDeLaSolicitudAbstract(archivoPdfDto);
    }

    @Override
    public List<SolicitudDomain> getListaSolicitudesService(Long idUsuario) {
        //Quiero filtar las solicitudes segun el Usuario que lo esta pidiendo
        return solicitudAbstract.getListaSolicitudesAbstract(idUsuario)
                .stream()
                .map(x -> mapperApplicationAbstract.mapearAbstract(x, SolicitudDomain.class))
                .toList();
    }

    @Override
    public void guardarSolicitudService(SolicitudDomain solicitudDomain) {
        //TODO
    }

    @Override
    public SolicitudDomain buscarSolicitudService(Long id) {
        //TODO
        return null;
    }
}
