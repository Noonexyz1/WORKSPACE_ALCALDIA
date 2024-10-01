package com.prototipo.infrastructure.impl;

import com.prototipo.application.modelDto.ArchivoPdfDto;
import com.prototipo.application.modelDto.SolicitudDto;
import com.prototipo.application.port.SolicitudAbstract;
import com.prototipo.infrastructure.persistence.db.entity.ArchivoPdf;
import com.prototipo.infrastructure.persistence.db.entity.Solicitud;
import com.prototipo.infrastructure.persistence.db.repository.ArchivoPdfRepository;
import com.prototipo.infrastructure.persistence.db.repository.SolicitudRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SolicitudImpl implements SolicitudAbstract {

    @Autowired
    private SolicitudRepository solicitudRepository;
    @Autowired
    private ArchivoPdfRepository archivoPdfRepository;
    @Autowired
    private ModelMapper modelMapper;

    //Tu unicamente deberias traerla Solicitud
    @Override
    public SolicitudDto solicitarFotocopiarAbstract(SolicitudDto solicitudDto) {
        Solicitud solicitud = modelMapper.map(solicitudDto, Solicitud.class);
        Solicitud solicitudResp = solicitudRepository.save(solicitud);
        return modelMapper.map(solicitudResp, SolicitudDto.class);
    }

    @Override
    public void guardarPdfDeLaSolicitudAbstract(ArchivoPdfDto archivoPdfDto) {
        ArchivoPdf archivoPdf = modelMapper.map(archivoPdfDto, ArchivoPdf.class);
        archivoPdfRepository.save(archivoPdf);
    }

    @Override
    public List<SolicitudDto> getListaSolicitudesAbstract(Long idUsuario) {
        return solicitudRepository.findAllByFkSolicitante_Id(idUsuario).stream()
                .map(x -> modelMapper.map(x, SolicitudDto.class))
                .toList();
    }

    @Override
    public void guardarSolicitudAbstract(SolicitudDto solicitudDto) {
        Solicitud solicitud = modelMapper.map(solicitudDto, Solicitud.class);
        solicitudRepository.save(solicitud);
    }

    @Override
    public SolicitudDto buscarSolicitudAbstract(Long id) {
        Solicitud solicitud = solicitudRepository.findById(id).orElseThrow();
        return modelMapper.map(solicitud, SolicitudDto.class);
    }
}
