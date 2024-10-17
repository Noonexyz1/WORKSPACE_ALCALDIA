package com.prototipo.infrastructure.impl;

import com.prototipo.application.modelDto.ArchivoPdfDto;
import com.prototipo.application.modelDto.SolicitudDto;
import com.prototipo.application.port.SolicitudAbstract;
import com.prototipo.domain.model.Solicitud;
import com.prototipo.infrastructure.persistence.db.entity.ArchivoPdfEntity;
import com.prototipo.infrastructure.persistence.db.entity.SolicitudEntity;
import com.prototipo.infrastructure.persistence.db.repository.ArchivoPdfRepository;
import com.prototipo.infrastructure.persistence.db.repository.SolicitudRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        SolicitudEntity solicitudEntity = modelMapper.map(solicitudDto, SolicitudEntity.class);
        SolicitudEntity solicitudEntityResp = solicitudRepository.save(solicitudEntity);
        return modelMapper.map(solicitudEntityResp, SolicitudDto.class);
    }

    @Override
    public void guardarPdfDeLaSolicitudAbstract(ArchivoPdfDto archivoPdfDto) {
        ArchivoPdfEntity archivoPdfEntity = modelMapper.map(archivoPdfDto, ArchivoPdfEntity.class);
        archivoPdfRepository.save(archivoPdfEntity);
    }

    @Override
    public List<SolicitudDto> getListaSolicitudesAbstract(Long idUsuario, Long page, Long size) {
        Pageable pageable = PageRequest.of(page.intValue(), size.intValue());
        List<SolicitudEntity> listSoliResp = solicitudRepository.findAllByFkSolicitante_Id(idUsuario, pageable);
        return listSoliResp.stream()
                .map(x -> modelMapper.map(x, SolicitudDto.class))
                .toList();
    }

    @Override
    public List<SolicitudDto> getListaSolicitudesByUnidad(Long idUnidad) {
        /*List<SolicitudEntity> listSoliResp = solicitudRepository.findAllByFkUnidad_Id(idUnidad);
        return listSoliResp.stream()
                .map(x -> modelMapper.map(x, SolicitudDto.class))
                .toList();*/
        return null;
    }

    @Override
    public void guardarSolicitudAbstract(SolicitudDto solicitudDto) {
        SolicitudEntity solicitudEntity = modelMapper.map(solicitudDto, SolicitudEntity.class);
        solicitudRepository.save(solicitudEntity);
    }

    @Override
    public SolicitudDto buscarSolicitudAbstract(Long id) {
        SolicitudEntity solicitudEntity = solicitudRepository.findById(id).orElseThrow();
        return modelMapper.map(solicitudEntity, SolicitudDto.class);
    }

    @Override
    public SolicitudDto buscarSolicitudByFkUnidad(Long idUnidad) {
        /*SolicitudEntity solicitudEntityResp = solicitudRepository.findByFkUnidad_Id(idUnidad);
        return modelMapper.map(solicitudEntityResp, SolicitudDto.class);*/
        return null;
    }
}
