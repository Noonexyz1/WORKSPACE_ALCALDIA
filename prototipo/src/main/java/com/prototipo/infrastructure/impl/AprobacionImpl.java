package com.prototipo.infrastructure.impl;

import com.prototipo.application.modelDto.AutorizacionDto;
import com.prototipo.application.modelDto.FinalizacionDto;
import com.prototipo.application.modelDto.SolicitudDto;
import com.prototipo.application.port.AprobacionAbstract;
import com.prototipo.infrastructure.persistence.db.entity.AutorizacionEntity;
import com.prototipo.infrastructure.persistence.db.entity.FinalizacionEntity;
import com.prototipo.infrastructure.persistence.db.entity.SolicitudEntity;
import com.prototipo.infrastructure.persistence.db.repository.AutorizacionRepository;
import com.prototipo.infrastructure.persistence.db.repository.FinalizacionRepository;
import com.prototipo.infrastructure.persistence.db.repository.SolicitudRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AprobacionImpl implements AprobacionAbstract {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private SolicitudRepository solicitudRepository;
    @Autowired
    private AutorizacionRepository autorizacionRepository;
    @Autowired
    private FinalizacionRepository finalizacionRepository;


    @Override
    public List<AutorizacionDto> listaDeSoliAutorizadasAbstractPage(
            Long idSupervisor,
            Long page,
            Long size,
            String byColumName) {

        List<AutorizacionEntity> autorizacionList = autorizacionRepository
                .buscarAutorizacionByIdResponsable(idSupervisor);

        return autorizacionList.stream()
                .map(x -> modelMapper.map(x, AutorizacionDto.class))
                .toList();
    }

    @Override
    public List<SolicitudDto> listaDeSolicitudesPendientesAbstractPage(
            Long idResponsable,
            Long page,
            Long size,
            String byColumName) {

        List<SolicitudEntity> listSolEnt = solicitudRepository
                .findAllSoliByIdResponsable(idResponsable);
        return listSolEnt.stream()
                .map(x -> modelMapper.map(x, SolicitudDto.class))
                .toList();
    }

    @Override
    public List<FinalizacionDto> listaDeAprobacionesFinalizadasAbstractPage(
            Long idResponsable,
            Long page,
            Long size,
            String byColumName) {

        // Ordena por ID en orden descendente
        Sort sort = Sort.by(Sort.Order.desc("id"));
        List<FinalizacionEntity> finalizacionList = finalizacionRepository.findAll(sort);
        return finalizacionList.stream()
                .map(x -> modelMapper.map(x, FinalizacionDto.class))
                .toList();
    }
}
