package com.prototipo.infrastructure.impl;

import com.prototipo.application.pager.PaginableIn;
import com.prototipo.application.pager.PaginableOut;
import com.prototipo.application.port.out.SolicitudAbstract;
import com.prototipo.domain.model.Solicitud;
import com.prototipo.infrastructure.persistence.db.entity.*;
import com.prototipo.infrastructure.persistence.db.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SolicitudImpl implements SolicitudAbstract {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private DetalleSolicitudRepository detalleSolicitudRepository;
    @Autowired
    private SolicitudRepository solicitudRepository;
    @Autowired
    private FinalizacionRepository finalizacionRepository;
    @Autowired
    private AutorizacionRepository autorizacionRepository;
    @Autowired
    private FotocopiaRepository fotocopiaRepository;
    @Autowired
    private ServicioFotocopiaRepository servicioFotocopiaRepository;


    @Override
    public PaginableOut<Solicitud> listaDeSolicitudesPendientesAbstractPageByIdSoli(PaginableIn paginableIn) {
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

        List<Solicitud> listResponse = pageResponse.getContent().stream()
                .map(x -> modelMapper.map(x, Solicitud.class))
                .toList();

        PaginableOut<Solicitud> paginableOut = PaginableOut
                .<Solicitud>builder()
                .content(listResponse)
                .totalPages(pageResponse.getTotalPages())
                .totalElements(pageResponse.getTotalElements())
                .build();

        return paginableOut;
    }

    @Override
    public PaginableOut<Solicitud> listaDeSolicitudesPendientesByIdResponsable(PaginableIn paginableIn) {
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

        List<Solicitud> listResponse = pageResponse.getContent().stream()
                .map(x -> modelMapper.map(x, Solicitud.class))
                .toList();

        PaginableOut<Solicitud> paginableOut = PaginableOut
                .<Solicitud>builder()
                .content(listResponse)
                .totalPages(pageResponse.getTotalPages())
                .totalElements(pageResponse.getTotalElements())
                .build();

        return paginableOut;
    }

    //Tu unicamente deberias traerla Solicitud
    @Override
    public Solicitud solicitarFotocopiarAbstract(Solicitud solicitud) {
        SolicitudEntity solicitudEntity = modelMapper.map(solicitud, SolicitudEntity.class);
        SolicitudEntity solicitudEntityResp = solicitudRepository.save(solicitudEntity);
        return modelMapper.map(solicitudEntityResp, Solicitud.class);
    }

    @Override
    public PaginableOut<Solicitud> getListaSolicitudesAutoriAbstract(PaginableIn paginableIn) {
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
                .findAllAutoriByIdUserUnidad(paginableIn.getId(), pageable);

        List<Solicitud> list = pageResponse.getContent().stream()
                .map(x -> modelMapper.map(x, Solicitud.class))
                .toList();

        PaginableOut<Solicitud> build = PaginableOut
                .<Solicitud>builder()
                .content(list)
                .totalPages(pageResponse.getTotalPages())
                .totalElements(pageResponse.getTotalElements())
                .build();

        return build;
    }

    @Override
    public PaginableOut<Solicitud> getListaSolicitudesFinaliAbstract(PaginableIn paginableIn) {
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
                .findAllFinaliByIdUserUnidad(paginableIn.getId(), pageable);

        List<Solicitud> list = pageResponse.getContent().stream()
                .map(x -> modelMapper.map(x, Solicitud.class))
                .toList();

        PaginableOut<Solicitud> build = PaginableOut
                .<Solicitud>builder()
                .content(list)
                .totalPages(pageResponse.getTotalPages())
                .totalElements(pageResponse.getTotalElements())
                .build();

        return build;
    }

    @Override
    public PaginableOut<Solicitud> getListaSolicitudesAbstract(PaginableIn paginableIn) {
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
                .findAllByIdUserUnidad(paginableIn.getId(), pageable);

        List<Solicitud> list = pageResponse.getContent().stream()
                .map(x -> modelMapper
                        .map(x, Solicitud.class))
                .toList();

        PaginableOut<Solicitud> build = PaginableOut
                .<Solicitud>builder()
                .content(list)
                .totalPages(pageResponse.getTotalPages())
                .totalElements(pageResponse.getTotalElements())
                .build();

        return build;
    }

    @Override
    public void guardarSolicitudAbstract(Solicitud solicitud) {
        SolicitudEntity solicitudEntity = modelMapper.map(solicitud, SolicitudEntity.class);
        solicitudRepository.save(solicitudEntity);
    }

    @Override
    public Solicitud buscarSolicitudByIdAbstract(Long id) {
        SolicitudEntity solicitudEntity = solicitudRepository
                .findById(id).orElseThrow();
        return modelMapper.map(solicitudEntity, Solicitud.class);
    }


}
