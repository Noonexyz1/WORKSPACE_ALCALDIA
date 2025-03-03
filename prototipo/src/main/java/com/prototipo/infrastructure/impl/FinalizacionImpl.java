package com.prototipo.infrastructure.impl;

import com.prototipo.application.modelDto.FinalizacionDto;
import com.prototipo.application.pager.PaginableIn;
import com.prototipo.application.pager.PaginableOut;
import com.prototipo.application.port.FinalizacionAbstract;
import com.prototipo.infrastructure.persistence.db.entity.AutorizacionEntity;
import com.prototipo.infrastructure.persistence.db.entity.FinalizacionEntity;
import com.prototipo.infrastructure.persistence.db.repository.FinalizacionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FinalizacionImpl implements FinalizacionAbstract {

    @Autowired
    private FinalizacionRepository finalizacionRepository;
    @Autowired
    private ModelMapper modelMapper;

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

    @Override
    public void guardarFinalizacionAbs(FinalizacionDto finalizacionDto) {
        AutorizacionEntity autorizacion = AutorizacionEntity.builder()
                .id(finalizacionDto.getFkAutorizacion().getId())
                .build();

        FinalizacionEntity finalizacionEntity = FinalizacionEntity.builder()
                .fecha(finalizacionDto.getFecha())
                .fkAutorizacion(autorizacion)
                .build();
        finalizacionRepository.save(finalizacionEntity);
    }
}
