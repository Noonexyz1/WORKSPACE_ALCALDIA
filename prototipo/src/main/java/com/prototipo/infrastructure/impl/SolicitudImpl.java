package com.prototipo.infrastructure.impl;

import com.prototipo.application.modelDto.*;
import com.prototipo.application.pager.PaginableIn;
import com.prototipo.application.pager.PaginableOut;
import com.prototipo.application.port.SolicitudAbstract;
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

    //Tu unicamente deberias traerla Solicitud
    @Override
    public SolicitudDto solicitarFotocopiarAbstract(SolicitudDto solicitudDto) {
        SolicitudEntity solicitudEntity = modelMapper.map(solicitudDto, SolicitudEntity.class);
        SolicitudEntity solicitudEntityResp = solicitudRepository.save(solicitudEntity);
        return modelMapper.map(solicitudEntityResp, SolicitudDto.class);
    }

    @Override
    public ServicioFotocopiaDto findServicioFotocopia(ServicioFotocopiaDto fkServicioFotocopia) {
        ServicioFotocopiaEntity serFotoEntity = servicioFotocopiaRepository
                .findByAnverColorTam(
                    fkServicioFotocopia.getAnverRever(),
                    fkServicioFotocopia.getColor(),
                    fkServicioFotocopia.getTamano()
                );

        return modelMapper.map(serFotoEntity, ServicioFotocopiaDto.class);
    }

    @Override
    public PaginableOut<SolicitudDto> getListaSolicitudesAutoriAbstract(PaginableIn paginableIn) {
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

        List<SolicitudDto> list = pageResponse.getContent().stream()
                .map(x -> modelMapper.map(x, SolicitudDto.class))
                .toList();

        PaginableOut<SolicitudDto> build = PaginableOut
                .<SolicitudDto>builder()
                .content(list)
                .totalPages(pageResponse.getTotalPages())
                .totalElements(pageResponse.getTotalElements())
                .build();

        return build;
    }

    @Override
    public PaginableOut<SolicitudDto> getListaSolicitudesFinaliAbstract(PaginableIn paginableIn) {
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

        List<SolicitudDto> list = pageResponse.getContent().stream()
                .map(x -> modelMapper.map(x, SolicitudDto.class))
                .toList();

        PaginableOut<SolicitudDto> build = PaginableOut
                .<SolicitudDto>builder()
                .content(list)
                .totalPages(pageResponse.getTotalPages())
                .totalElements(pageResponse.getTotalElements())
                .build();

        return build;
    }

    @Override
    public PaginableOut<SolicitudDto> getListaSolicitudesAbstract(PaginableIn paginableIn) {
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

        List<SolicitudDto> list = pageResponse.getContent().stream()
                .map(x -> modelMapper
                        .map(x, SolicitudDto.class))
                .toList();

        PaginableOut<SolicitudDto> build = PaginableOut
                .<SolicitudDto>builder()
                .content(list)
                .totalPages(pageResponse.getTotalPages())
                .totalElements(pageResponse.getTotalElements())
                .build();

        return build;
    }

    @Override
    public void guardarSolicitudAbstract(SolicitudDto solicitudDto) {
        SolicitudEntity solicitudEntity = modelMapper.map(solicitudDto, SolicitudEntity.class);
        solicitudRepository.save(solicitudEntity);
    }

    @Override
    public SolicitudDto buscarSolicitudByIdAbstract(Long id) {
        SolicitudEntity solicitudEntity = solicitudRepository
                .findById(id).orElseThrow();
        return modelMapper.map(solicitudEntity, SolicitudDto.class);
    }


}
