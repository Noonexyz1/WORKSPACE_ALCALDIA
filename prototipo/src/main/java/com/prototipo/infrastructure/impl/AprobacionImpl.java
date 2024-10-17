package com.prototipo.infrastructure.impl;

import com.prototipo.application.modelDto.AprobacionDto;
import com.prototipo.application.port.AprobacionAbstract;
import com.prototipo.infrastructure.persistence.db.entity.AprobacionEntity;
import com.prototipo.infrastructure.persistence.db.repository.AprobacionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        AprobacionEntity aprobacionEntity = modelMapper.map(aprobacionDto, AprobacionEntity.class);
        AprobacionEntity aprobacionEntityResp = aprobacionRepository.save(aprobacionEntity);
        AprobacionDto aprobacionDtoResp = modelMapper.map(aprobacionEntityResp, AprobacionDto.class);
        return aprobacionDtoResp;
    }

    @Override
    public AprobacionDto findAprovacionByIdSoliAbstract(Long id) {
        AprobacionEntity aprobacionEntity = aprobacionRepository.findById(id).orElseThrow();
        return modelMapper.map(aprobacionEntity, AprobacionDto.class);
    }

    @Override
    public List<AprobacionDto> listaDeSolicitudesAbstract() {
        List<AprobacionEntity> aprobacionEntities = aprobacionRepository.findAll();
        return aprobacionEntities.stream()
                .map(x -> modelMapper.map(x, AprobacionDto.class))
                .toList();
    }

    @Override
    public List<AprobacionDto> listaDeSolicitudesByFkSoliAbstract(Long idSoli) {
        /*List<AprobacionEntity> listAproResp = aprobacionRepository.findByfkSolicitud_Id(idSoli);
        return listAproResp.stream()
                .map(x -> modelMapper.map(x, AprobacionDto.class))
                .toList();*/
        return null;
    }

    @Override
    public List<AprobacionDto> listaDeSolicitudesByUnidad(String nombreUnidad) {
        /*List<AprobacionEntity> listApro = aprobacionRepository.findAprobacionesByUnidadNombre(nombreUnidad);
        return listApro.stream()
                .map(x -> modelMapper.map(x, AprobacionDto.class))
                .toList();*/
        return null;
    }

    @Override
    public List<AprobacionDto> listaDeAprobacionesPendientesAbstractPage(Long idSupervisor, Long page, Long size, String byColumName) {
        //Sort sort = Sort.by(Sort.Direction.DESC, byColumName);
        Pageable pageable = PageRequest.of(page.intValue(), size.intValue());
        //Cuando le envias un pageable, este te retorna un pageable
        List<AprobacionEntity> listAprobacionEntity = aprobacionRepository
                .findAprobacionesByResponPendiente(idSupervisor, pageable);
        return listAprobacionEntity.stream()
                .map(x -> modelMapper.map(x, AprobacionDto.class))
                .toList();
    }

    @Override
    public List<AprobacionDto> listaDeAprobacionesAprobadasAbstractPage(Long idResponsable, Long page, Long size, String byColumName) {
        //Sort sort = Sort.by(Sort.Direction.DESC, byColumName);
        Pageable pageable = PageRequest.of(page.intValue(), size.intValue());
        //Cuando le envias un pageable, este te retorna un pageable
        List<AprobacionEntity> listAprobacionEntity = aprobacionRepository
                .findAprobacionesByResponAprobadas(idResponsable, pageable);
        return listAprobacionEntity.stream()
                .map(x -> modelMapper.map(x, AprobacionDto.class))
                .toList();
    }

    @Override
    public List<AprobacionDto> listaDeAprobacionesRechazadasAbstractPage(Long idSupervisor, Long page, Long size, String byColumName) {
        //Sort sort = Sort.by(Sort.Direction.DESC, byColumName);
        Pageable pageable = PageRequest.of(page.intValue(), size.intValue());
        //Cuando le envias un pageable, este te retorna un pageable
        List<AprobacionEntity> listAprobacionEntity = aprobacionRepository
                .findAprobacionesByResponRechazadas(idSupervisor, pageable);
        return listAprobacionEntity.stream()
                .map(x -> modelMapper.map(x, AprobacionDto.class))
                .toList();
    }
}
