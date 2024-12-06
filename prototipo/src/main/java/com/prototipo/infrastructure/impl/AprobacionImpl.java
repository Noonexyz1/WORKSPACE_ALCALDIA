package com.prototipo.infrastructure.impl;

import com.prototipo.application.modelDto.AprobacionDto;
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
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

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
    public AutorizacionDto findAutorizacionByIdAbstract(Long idAutorizacion) {
        Optional<AutorizacionEntity> autorizacion = autorizacionRepository
                .findById(idAutorizacion);
        return modelMapper
                .map(autorizacion.orElse(null), AutorizacionDto.class);
    }

    @Override
    public void guardarAutorizacionAbstract(AutorizacionDto autorizacionDto) {
        //Es posible manejar algo de logica aqui, con tal de cumplir
        //las reglas de negocio del dominio, asi como lo hacia
        //con el controller jaja

        //Esta entidad esta dentro del contexto de JPA :)
        AutorizacionEntity autorizacion = autorizacionRepository
                .findById(autorizacionDto.getId())
                .orElseThrow();

        autorizacion.setFinaliFlag(
                autorizacionDto.getFinaliFlag()
        );

        autorizacionRepository.save(autorizacion);
    }

    @Override
    public List<SolicitudDto> listaDeAprobacionesPendientesAbstractPage(
            Long idResponsable,
            Long page,
            Long size,
            String byColumName) {

        List<SolicitudEntity> listSolEnt = solicitudRepository
                .findAllByIdUserUnidadRespon(idResponsable);
        return listSolEnt.stream()
                .map(x -> modelMapper.map(x, SolicitudDto.class))
                .toList();
    }

    @Override
    public List<AprobacionDto> listaDeAprobacionesAprobadasAbstractPage(
            Long idResponsable,
            Long page,
            Long size,
            String byColumName) {

        return null;
    }

    @Override
    public List<AprobacionDto> listaDeAprobacionesRechazadasAbstractPage(
            Long idSupervisor,
            Long page,
            Long size,
            String byColumName) {

        return null;
    }

    @Override
    public List<FinalizacionDto> listaDeAprobacionesFinalizadasAbstractPage(
            Long idSupervisor,
            Long page,
            Long size,
            String byColumName) {

        List<FinalizacionEntity> finalizacionList = finalizacionRepository.findAll();
        return finalizacionList.stream()
                .map(x -> modelMapper.map(x, FinalizacionDto.class))
                .toList();
    }

}
