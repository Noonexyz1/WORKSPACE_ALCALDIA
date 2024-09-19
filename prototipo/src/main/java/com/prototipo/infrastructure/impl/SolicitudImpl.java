package com.prototipo.infrastructure.impl;

import com.prototipo.application.modelDto.SolicitudDto;
import com.prototipo.application.modelDto.UnidadDto;
import com.prototipo.application.modelDto.UsuarioDto;
import com.prototipo.application.port.SolicitudAbstract;
import com.prototipo.infrastructure.persistence.db.entity.ArchivoPdf;
import com.prototipo.infrastructure.persistence.db.entity.Solicitud;
import com.prototipo.infrastructure.persistence.db.entity.Unidad;
import com.prototipo.infrastructure.persistence.db.entity.Usuario;
import com.prototipo.infrastructure.persistence.db.repository.ArchivoPdfRepository;
import com.prototipo.infrastructure.persistence.db.repository.SolicitudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SolicitudImpl implements SolicitudAbstract {

    @Autowired
    private SolicitudRepository solicitudRepository;
    @Autowired
    private ArchivoPdfRepository archivoPdfRepository;

    @Override
    public void solicitarFotocopiarAbstract(SolicitudDto solicitudDto) {
        //TODO -------------realizar las implementaciones
        //Solo necesitas el ID, y aunque los demas atributos sea nulos,
        //JPA traera ese objeto con ese ID internamente y lo pasara a la BD ;)
        UsuarioDto usuarioDto = solicitudDto.getSolicitante();
        Usuario usuario = Usuario.builder()
                .id(usuarioDto.getId())
                .build();

        //Solo necesitas el ID, y aunque los demas atributos sea nulos,
        //JPA traera ese objeto con ese ID internamente y lo pasara a la BD ;)
        UnidadDto unidadDto = solicitudDto.getUnidad();
        Unidad unidad = Unidad.builder()
                .id(unidadDto.getId())
                .build();


        Solicitud solicitud = Solicitud.builder()
                .nroDeCopias(solicitudDto.getNroDeCopias())
                .tipoDeDocumento(solicitudDto.getTipoDeDocumento())
                .nroDePaginas(solicitudDto.getNroDePaginas())
                //estoy hardcoeando por fines practicos
                .estadoSolicitud(solicitudDto.getEstadoSolicitud())
                .notificacionToAprobar(solicitudDto.getNotificacionToAprobar())
                .fk_solicitante(usuario)
                .fk_unidad(unidad)
                //TODO--- falta la prueba de los archivos
                //Hacer prueba esto
                .build();


        //Luego de llenar la solicitud hay que llenar la tabla archivo_pdf porque
        //la relacion es de una solicitud tiene muchos archivos pdfs
        //Una vez guardando esta instancia, JPA me va ha devolver el objeto guardado de la BD con el
        //ID que se le ha asignado
        Solicitud solicitudPersist = solicitudRepository.save(solicitud);

        List<ArchivoPdf> archivoPdfs = solicitudDto.getArchivosPdf()
                .stream()
                .map(x -> ArchivoPdf.builder()
                                .archivo(x.getArchivo())
                                .build()
                )
                .toList();

        archivoPdfs.forEach(x -> {
            x.setFk_solicitud(solicitudPersist);
            archivoPdfRepository.save(x);
        });

    }

    @Override
    public List<SolicitudDto> getListaSolicitudesAbstract() {
        return List.of();
    }

    @Override
    public List<SolicitudDto> getSolicitudesOperadorAbstract() {
        return List.of();
    }
}
