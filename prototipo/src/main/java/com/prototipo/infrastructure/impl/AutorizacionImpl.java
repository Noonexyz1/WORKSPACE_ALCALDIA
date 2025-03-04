package com.prototipo.infrastructure.impl;

import com.prototipo.application.pager.PaginableIn;
import com.prototipo.application.pager.PaginableOut;
import com.prototipo.application.port.out.AutorizacionAbstract;
import com.prototipo.domain.model.Autorizacion;
import com.prototipo.infrastructure.persistence.db.entity.AutorizacionEntity;
import com.prototipo.infrastructure.persistence.db.entity.SolicitudEntity;
import com.prototipo.infrastructure.persistence.db.entity.UsuarioUnidadEntity;
import com.prototipo.infrastructure.persistence.db.repository.AutorizacionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AutorizacionImpl implements AutorizacionAbstract {

    @Autowired
    private AutorizacionRepository autorizacionRepository;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public PaginableOut<Autorizacion> listaDeSoliAutorizadasAbstractPageByIdSoli(PaginableIn paginableIn) {
        Sort sort = Sort.by(
                Sort.Direction.fromString(paginableIn.getDirection()),
                paginableIn.getSortBy()
        );

        Pageable pageable = PageRequest.of(
                paginableIn.getPage().intValue(),
                paginableIn.getSize().intValue(),
                sort
        );

        Page<AutorizacionEntity> autorizacionList = autorizacionRepository
                .buscarAutorizacionByIdResponsableByIdSoli(paginableIn.getId(), pageable);

        List<Autorizacion> list = autorizacionList.getContent().stream()
                .map(x -> modelMapper.map(x, Autorizacion.class))
                .toList();

        PaginableOut<Autorizacion> paginableOut = PaginableOut
                .<Autorizacion>builder()
                .content(list)
                .totalPages(autorizacionList.getTotalPages())
                .totalElements(autorizacionList.getTotalElements())
                .build();

        return paginableOut;
    }

    @Override
    public PaginableOut<Autorizacion> listaDeSoliAutorizadasAbstractPageByIdResponsable(PaginableIn paginableIn) {
        Sort sort = Sort.by(
                Sort.Direction.fromString(paginableIn.getDirection()),
                paginableIn.getSortBy()
        );

        Pageable pageable = PageRequest.of(
                paginableIn.getPage().intValue(),
                paginableIn.getSize().intValue(),
                sort
        );

        Page<AutorizacionEntity> autorizacionList = autorizacionRepository
                .buscarAutorizacionByIdResponsable(paginableIn.getId(), pageable);

        List<Autorizacion> list = autorizacionList.getContent().stream()
                .map(x -> modelMapper.map(x, Autorizacion.class))
                .toList();

        PaginableOut<Autorizacion> paginableOut = PaginableOut
                .<Autorizacion>builder()
                .content(list)
                .totalPages(autorizacionList.getTotalPages())
                .totalElements(autorizacionList.getTotalElements())
                .build();

        return paginableOut;
    }

    @Override
    public void guardarAutorizacionAbs(Autorizacion autorizacionDto) {
        UsuarioUnidadEntity usuarioResponsable = UsuarioUnidadEntity.builder()
                .id(autorizacionDto.getFkUsuarioResponsable().getId())
                .build();

        SolicitudEntity solicitudEntity = SolicitudEntity.builder()
                .id(autorizacionDto.getFkSolicitud().getId())
                .build();

        AutorizacionEntity autorizacion = AutorizacionEntity.builder()
                .id(autorizacionDto.getId())
                .fecha(autorizacionDto.getFecha())
                .finaliFlag(autorizacionDto.getFinaliFlag())
                .fkUsuarioResponsable(usuarioResponsable)
                .fkSolicitud(solicitudEntity)
                .build();

        autorizacionRepository.save(autorizacion);
    }

    @Override
    public Autorizacion buscarAutorizacionByIdAbs(Long idAutorizacion) {
        AutorizacionEntity autorizacion = autorizacionRepository
                .findById(idAutorizacion).get();
        return modelMapper.map(autorizacion, Autorizacion.class);
    }

    @Override
    public Autorizacion findAutorizacionByIdSoli(Long idSolicitud) {
        AutorizacionEntity autorizacionEntity = autorizacionRepository
                .buscarAutorizacionByIdSoli(idSolicitud);
        return modelMapper.map(autorizacionEntity, Autorizacion.class);
    }
}
