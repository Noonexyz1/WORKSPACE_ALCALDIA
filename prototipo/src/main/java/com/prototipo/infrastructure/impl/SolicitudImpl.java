package com.prototipo.infrastructure.impl;

import com.prototipo.application.modelDto.ArchivoPdfDto;
import com.prototipo.application.modelDto.DetalleSolicitudDto;
import com.prototipo.application.modelDto.SolicitudDto;
import com.prototipo.application.port.SolicitudAbstract;
import com.prototipo.infrastructure.persistence.db.entity.DetalleSolicitudEntity;
import com.prototipo.infrastructure.persistence.db.entity.SolicitudEntity;
import com.prototipo.infrastructure.persistence.db.repository.DetalleSolicitudRepository;
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
    private ModelMapper modelMapper;
    @Autowired
    private DetalleSolicitudRepository detalleSolicitudRepository;

    //Tu unicamente deberias traerla Solicitud
    @Override
    public SolicitudDto solicitarFotocopiarAbstract(SolicitudDto solicitudDto) {

        return null;
    }

    @Override
    public void guardarPdfDeLaSolicitudAbstract(ArchivoPdfDto archivoPdfDto) {

    }

    @Override
    public void guardarRegistroSolicitud(DetalleSolicitudDto detalleSolicitudDto) {
        DetalleSolicitudEntity solicitud = modelMapper
                .map(detalleSolicitudDto, DetalleSolicitudEntity.class);
        detalleSolicitudRepository.save(solicitud);
    }

    @Override
    public List<SolicitudDto> getListaSolicitudesAbstract(Long idUsuario, Long page, Long size) {

        return null;
    }

    @Override
    public List<SolicitudDto> getListaSolicitudesByUnidad(Long idUnidad) {

        return null;
    }

    @Override
    public void guardarSolicitudAbstract(SolicitudDto solicitudDto) {

    }

    @Override
    public SolicitudDto buscarSolicitudAbstract(Long id) {

        return null;
    }

    @Override
    public SolicitudDto buscarSolicitudByFkUnidad(Long idUnidad) {

        return null;
    }

    @Override
    public List<DetalleSolicitudDto> getListaDetalleSolicitudAbstract(Long idSolicitud) {
        List<DetalleSolicitudEntity> list = detalleSolicitudRepository
                .findAllBySolicitudId(idSolicitud);
        return list.stream()
                .map(x -> modelMapper.map(x, DetalleSolicitudDto.class))
                .toList();
    }
}
