package com.prototipo.infrastructure.impl;

import com.prototipo.application.modelDto.AutorizacionDto;
import com.prototipo.application.modelDto.FinalizacionDto;
import com.prototipo.application.modelDto.SolicitudDto;
import com.prototipo.application.pager.PaginableIn;
import com.prototipo.application.pager.PaginableOut;
import com.prototipo.application.port.AprobacionAbstract;
import com.prototipo.infrastructure.persistence.db.entity.AutorizacionEntity;
import com.prototipo.infrastructure.persistence.db.entity.FinalizacionEntity;
import com.prototipo.infrastructure.persistence.db.entity.SolicitudEntity;
import com.prototipo.infrastructure.persistence.db.repository.AutorizacionRepository;
import com.prototipo.infrastructure.persistence.db.repository.FinalizacionRepository;
import com.prototipo.infrastructure.persistence.db.repository.SolicitudRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public PaginableOut<SolicitudDto> listaDeSolicitudesPendientesAbstractPageByIdSoli(PaginableIn paginableIn) {
        Sort sort = Sort.by(
                Sort.Direction.fromString(paginableIn.getDirection()),
                paginableIn.getSortBy()
        );

        Pageable pageable = PageRequest.of(
                paginableIn.getPage().intValue(),
                paginableIn.getSize().intValue(),
                sort
        );

        Page<SolicitudEntity> pageResponse = solicitudRepository
                .findAllSoliByIdResponsableByIdSoli(paginableIn.getId(), pageable);

        List<SolicitudDto> listResponse = pageResponse.getContent().stream()
                .map(x -> modelMapper.map(x, SolicitudDto.class))
                .toList();

        PaginableOut<SolicitudDto> paginableOut = PaginableOut
                .<SolicitudDto>builder()
                .content(listResponse)
                .totalPages(pageResponse.getTotalPages())
                .totalElements(pageResponse.getTotalElements())
                .build();

        return paginableOut;
    }

    @Override
    public PaginableOut<AutorizacionDto> listaDeSoliAutorizadasAbstractPageByIdSoli(PaginableIn paginableIn) {
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

        List<AutorizacionDto> list = autorizacionList.getContent().stream()
                .map(x -> modelMapper.map(x, AutorizacionDto.class))
                .toList();

        PaginableOut<AutorizacionDto> paginableOut = PaginableOut
                .<AutorizacionDto>builder()
                .content(list)
                .totalPages(autorizacionList.getTotalPages())
                .totalElements(autorizacionList.getTotalElements())
                .build();

        return paginableOut;
    }

    @Override
    public PaginableOut<FinalizacionDto> listaDeFinalizacionesAbstractPageByIdSoli(PaginableIn paginableIn) {
        Sort sort = Sort.by(
                Sort.Direction.fromString(paginableIn.getDirection()),
                paginableIn.getSortBy()
        );

        Pageable pageable = PageRequest.of(
                paginableIn.getPage().intValue(),
                paginableIn.getSize().intValue(),
                sort
        );

        Page<FinalizacionEntity> finalizacionList = finalizacionRepository
                .findFinalizacionSoliByIdSoli(paginableIn.getId(), pageable);

        List<FinalizacionDto> list = finalizacionList.getContent().stream()
                .map(x -> modelMapper.map(x, FinalizacionDto.class))
                .toList();

        PaginableOut<FinalizacionDto> paginableOut = PaginableOut
                .<FinalizacionDto>builder()
                .content(list)
                .totalPages(finalizacionList.getTotalPages())
                .totalElements(finalizacionList.getTotalElements())
                .build();

        return paginableOut;
    }

    @Override
    public PaginableOut<SolicitudDto> listaDeSolicitudesPendientesByIdResponsable(PaginableIn paginableIn) {
        Sort sort = Sort.by(
                Sort.Direction.fromString(paginableIn.getDirection()),
                paginableIn.getSortBy()
        );

        Pageable pageable = PageRequest.of(
                paginableIn.getPage().intValue(),
                paginableIn.getSize().intValue(),
                sort
        );

        Page<SolicitudEntity> pageResponse = solicitudRepository
                .findAllSoliByIdResponsable(paginableIn.getId(), pageable);

        List<SolicitudDto> listResponse = pageResponse.getContent().stream()
                .map(x -> modelMapper.map(x, SolicitudDto.class))
                .toList();

        PaginableOut<SolicitudDto> paginableOut = PaginableOut
                .<SolicitudDto>builder()
                .content(listResponse)
                .totalPages(pageResponse.getTotalPages())
                .totalElements(pageResponse.getTotalElements())
                .build();

        return paginableOut;
    }

    @Override
    public PaginableOut<AutorizacionDto> listaDeSoliAutorizadasAbstractPageByIdResponsable(PaginableIn paginableIn) {
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

        List<AutorizacionDto> list = autorizacionList.getContent().stream()
                .map(x -> modelMapper.map(x, AutorizacionDto.class))
                .toList();

        PaginableOut<AutorizacionDto> paginableOut = PaginableOut
                .<AutorizacionDto>builder()
                .content(list)
                .totalPages(autorizacionList.getTotalPages())
                .totalElements(autorizacionList.getTotalElements())
                .build();

        return paginableOut;
    }

    @Override
    public PaginableOut<FinalizacionDto> listaDeFinalizacionesAbstractPageByIdResponsable(PaginableIn paginableIn) {
        Sort sort = Sort.by(
                Sort.Direction.fromString(paginableIn.getDirection()),
                paginableIn.getSortBy()
        );

        Pageable pageable = PageRequest.of(
                paginableIn.getPage().intValue(),
                paginableIn.getSize().intValue(),
                sort
        );

        Page<FinalizacionEntity> finalizacionList = finalizacionRepository.findAll(pageable);

        List<FinalizacionDto> list = finalizacionList.getContent().stream()
                .map(x -> modelMapper.map(x, FinalizacionDto.class))
                .toList();

        PaginableOut<FinalizacionDto> paginableOut = PaginableOut
                .<FinalizacionDto>builder()
                .content(list)
                .totalPages(finalizacionList.getTotalPages())
                .totalElements(finalizacionList.getTotalElements())
                .build();

        return paginableOut;
    }
}
