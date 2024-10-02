package com.prototipo.infrastructure.impl;

import com.prototipo.application.modelDto.AprobacionDto;
import com.prototipo.application.port.AprobacionAbstract;
import com.prototipo.infrastructure.persistence.db.entity.Aprobacion;
import com.prototipo.infrastructure.persistence.db.repository.AprobacionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AprobacionImpl implements AprobacionAbstract {

    @Autowired
    private AprobacionRepository aprobacionRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AprobacionDto guardarAprobacionAbstract(AprobacionDto aprobacionDto) {
        Aprobacion aprobacion = modelMapper.map(aprobacionDto, Aprobacion.class);
        Aprobacion aprobacionResp = aprobacionRepository.save(aprobacion);
        AprobacionDto aprobacionDtoResp = modelMapper.map(aprobacionResp, AprobacionDto.class);
        return aprobacionDtoResp;
    }

    @Override
    public AprobacionDto findAprovacionByIdSoliAbstract(Long id) {
        Aprobacion aprobacion = aprobacionRepository.findById(id).orElseThrow();
        return modelMapper.map(aprobacion, AprobacionDto.class);
    }

    @Override
    public List<AprobacionDto> listaDeSolicitudesAbstract() {
        List<Aprobacion> aprobacions = aprobacionRepository.findAll();
        return aprobacions.stream()
                .map(x -> modelMapper.map(x, AprobacionDto.class))
                .toList();
    }

    @Override
    public List<AprobacionDto> listaDeSolicitudesByFkSoliAbstract(Long idSoli) {
        List<Aprobacion> listAproResp = aprobacionRepository.findByfkSolicitud_Id(idSoli);
        return listAproResp.stream()
                .map(x -> modelMapper.map(x, AprobacionDto.class))
                .toList();
    }

    @Override
    public List<AprobacionDto> listaDeSolicitudesByUnidad(String nombreUnidad) {
        List<Aprobacion> listApro = aprobacionRepository.findAprobacionesByUnidadNombre(nombreUnidad);
        return listApro.stream().map(x -> modelMapper.map(x, AprobacionDto.class)).toList();
    }
}
