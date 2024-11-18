package com.prototipo.infrastructure.impl;

import com.prototipo.application.modelDto.AprobacionDto;
import com.prototipo.application.port.AprobacionAbstract;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AprobacionImpl implements AprobacionAbstract {

    @Autowired
    private ModelMapper modelMapper;

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
    public List<AprobacionDto> listaDeAprobacionesPendientesAbstractPage(Long idSupervisor, Long page, Long size, String byColumName) {

        return null;
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
