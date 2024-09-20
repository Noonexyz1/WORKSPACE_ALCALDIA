package com.prototipo.application.adapter;

import com.prototipo.application.modelDto.ArchivoPdfDto;
import com.prototipo.application.modelDto.SolicitudDto;
import com.prototipo.application.modelDto.UnidadDto;
import com.prototipo.application.modelDto.UsuarioDto;
import com.prototipo.application.port.SolicitudAbstract;
import com.prototipo.application.useCase.SolicitudService;
import com.prototipo.domain.model.Solicitud;
import com.prototipo.domain.model.Unidad;
import com.prototipo.domain.model.Usuario;

import java.util.List;

public class SolicitudAdapter implements SolicitudService {

    private SolicitudAbstract solicitudAbstract;

    public SolicitudAdapter(SolicitudAbstract solicitudAbstract){
        this.solicitudAbstract = solicitudAbstract;
    }


    @Override
    public void solicitarFotocopiarService(Solicitud solicitud) {
        //TODO Mapeado de instancias
        Unidad unidad = solicitud.getUnidad();
        UnidadDto unidadDto = UnidadDto.builder()
                .id(unidad.getId())
                .build();

        Usuario usuario = solicitud.getUsuario();
        UsuarioDto usuarioDto = UsuarioDto.builder()
                .id(usuario.getId())
                .build();

        List<ArchivoPdfDto> archivoPdfDtos = solicitud.getListArvhicosPDF()
                .stream()
                .map(x -> ArchivoPdfDto.builder()
                        .archivo(x.getArchivo())
                        .build()
                )
                .toList();

        SolicitudDto solicitudDto = SolicitudDto.builder()
                .nroDeCopias(solicitud.getNroDeCopias())
                .tipoDeDocumento(solicitud.getTipoDeDocumento())
                .nroDePaginas(solicitud.getNroDePaginas())
                .estadoSolicitud("Pendiente")
                .notificacionToAprobar("Pendiente")
                .unidad(unidadDto)
                .solicitante(usuarioDto)
                .archivosPdf(archivoPdfDtos)
                .build();

        solicitudAbstract.solicitarFotocopiarAbstract(solicitudDto);
    }

    @Override
    public List<Solicitud> getListaSolicitudesService() {
        //Hacer los mapeos correspondientes
        List<Solicitud> solicitud = solicitudAbstract.getListaSolicitudesAbstract()
                .stream()
                .map(x -> Solicitud.builder()
                        .id(x.getId())
                        .nroDeCopias(x.getNroDeCopias())
                        .tipoDeDocumento(x.getTipoDeDocumento())
                        .nroDePaginas(x.getNroDePaginas())
                        .estadoSolicitud(x.getEstadoSolicitud())
                        .notificacionToAprobar(x.getNotificacionToAprobar())
                        .usuario(Usuario.builder()
                                .id(x.getSolicitante().getId())
                                .nombres(x.getSolicitante().getNombres())
                                .apellidos(x.getSolicitante().getApellidos())
                                .build()
                        )
                        .unidad(Unidad.builder()
                                .id(x.getUnidad().getId())
                                .nombre(x.getUnidad().getNombre())
                                .direccion(x.getUnidad().getDireccion())
                                .build())
                        .build()
                )
                .toList();

        return solicitud;
    }

    @Override
    public List<Solicitud> getSolicitudesOperadorService() {
        //Hacer los mapeos correspondientes
        List<SolicitudDto> solicitudDtoList = solicitudAbstract.getSolicitudesOperadorAbstract();
        return List.of();
    }
}
