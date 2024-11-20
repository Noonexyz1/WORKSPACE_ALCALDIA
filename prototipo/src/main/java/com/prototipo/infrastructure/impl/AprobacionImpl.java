package com.prototipo.infrastructure.impl;

import com.prototipo.application.modelDto.AprobacionDto;
import com.prototipo.application.modelDto.SolicitudDto;
import com.prototipo.application.port.AprobacionAbstract;
import com.prototipo.infrastructure.persistence.db.entity.SolicitudEntity;
import com.prototipo.infrastructure.persistence.db.repository.SolicitudRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AprobacionImpl implements AprobacionAbstract {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private SolicitudRepository solicitudRepository;


    @Override
    public AprobacionDto guardarAprobacionAbstract(AprobacionDto aprobacionDto) {

        return null;
    }

    @Override
    public AprobacionDto findAprovacionByIdSoliAbstract(Long id) {

        return null;
    }

    @Override
    public List<AprobacionDto> listaDeSolicitudesAbstract() {

        return null;
    }

    @Override
    public List<AprobacionDto> listaDeSolicitudesByFkSoliAbstract(Long idSoli) {

        return null;
    }

    @Override
    public List<AprobacionDto> listaDeSolicitudesByUnidad(String nombreUnidad) {

        return null;
    }

    @Override
    public List<SolicitudDto> listaDeAprobacionesPendientesAbstractPage(Long idResponsable, Long page, Long size, String byColumName) {
        List<SolicitudEntity> listSolEnt = solicitudRepository.findAllByIdUserUnidadRespon(idResponsable);
        return listSolEnt.stream()
                .map(x -> modelMapper.map(x, SolicitudDto.class))
                .toList();
    }

    @Override
    public List<AprobacionDto> listaDeAprobacionesAprobadasAbstractPage(Long idResponsable, Long page, Long size, String byColumName) {

        return null;
    }

    @Override
    public List<AprobacionDto> listaDeAprobacionesRechazadasAbstractPage(Long idSupervisor, Long page, Long size, String byColumName) {

        return null;
    }
}
