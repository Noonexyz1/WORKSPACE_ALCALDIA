package com.prototipo.application.adapter;

import com.prototipo.application.mapper.MapperApplicationAbstract;
import com.prototipo.application.modelDto.ArchivoPdfDto;
import com.prototipo.application.modelDto.SolicitudDto;
import com.prototipo.application.modelDto.UnidadDto;
import com.prototipo.application.modelDto.UsuarioDto;
import com.prototipo.application.port.SolicitudAbstract;
import com.prototipo.application.useCase.SolicitudService;
import com.prototipo.domain.enums.EstadoByOperadorEnum;
import com.prototipo.domain.enums.EstadoByResponsableEnum;
import com.prototipo.domain.model.ArchivoPdfDomain;
import com.prototipo.domain.model.SolicitudDomain;

import java.util.List;

public class SolicitudAdapter implements SolicitudService {

    private SolicitudAbstract solicitudAbstract;
    private MapperApplicationAbstract mapperApplicationAbstract;

    public SolicitudAdapter(SolicitudAbstract solicitudAbstract,
                            MapperApplicationAbstract mapperApplicationAbstract){

        this.solicitudAbstract = solicitudAbstract;
        this.mapperApplicationAbstract = mapperApplicationAbstract;
    }


    @Override
    public void solicitarFotocopiarService(SolicitudDomain solicitudDomain, List<ArchivoPdfDomain> listPdf) {
        //Mapeo de instancias
        UnidadDto unidadDto = mapperApplicationAbstract.mapearAbstract(solicitudDomain.getFkUnidad(), UnidadDto.class);
        UsuarioDto usuarioDto = mapperApplicationAbstract.mapearAbstract(solicitudDomain.getFkSolicitante(), UsuarioDto.class);

        SolicitudDto solicitudDto = mapperApplicationAbstract.mapearAbstract(solicitudDomain, SolicitudDto.class);
        solicitudDto.setEstadoByResponsable(EstadoByResponsableEnum.PENDIENTE.getNombre());
        solicitudDto.setEstadoByOperador(EstadoByOperadorEnum.PENDIENTE.getNombre());

        SolicitudDto solicitudDtoResp = solicitudAbstract.solicitarFotocopiarAbstract(solicitudDto);
        SolicitudDomain solicitudDomainResp = mapperApplicationAbstract.mapearAbstract(solicitudDtoResp, SolicitudDomain.class);

        listPdf.forEach(x -> {
            x.setFkSolicitud(solicitudDomainResp);
            guardarPdfDeLaSolicitudAbstract(x);
        });
    }

    @Override
    public void guardarPdfDeLaSolicitudAbstract(ArchivoPdfDomain archivoPdfDomain) {
        ArchivoPdfDto archivoPdfDto = mapperApplicationAbstract.mapearAbstract(archivoPdfDomain, ArchivoPdfDto.class);
        solicitudAbstract.guardarPdfDeLaSolicitudAbstract(archivoPdfDto);
    }

    @Override
    public List<SolicitudDomain> getListaSolicitudesService() {
        return solicitudAbstract.getListaSolicitudesAbstract()
                .stream()
                .map(x -> mapperApplicationAbstract.mapearAbstract(x, SolicitudDomain.class))
                .toList();
    }
}
