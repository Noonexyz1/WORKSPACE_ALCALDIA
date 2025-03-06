package com.prototipo.infrastructure.persistence.db.adapter;

import com.prototipo.application.model.PaginableIn;
import com.prototipo.application.model.PaginableOut;
import com.prototipo.application.port.out.FinalizacionAbstract;
import com.prototipo.domain.model.Finalizacion;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class FinalizacionImpl implements FinalizacionAbstract {

    @Autowired
    private FinalizacionRepository finalizacionRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PaginableOut<Finalizacion> listaDeFinalizacionesAbstractPageByIdSoli(PaginableIn paginableIn) {
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

        List<Finalizacion> list = finalizacionList.getContent().stream()
                .map(x -> modelMapper.map(x, Finalizacion.class))
                .toList();

        PaginableOut<Finalizacion> paginableOut = PaginableOut
                .<Finalizacion>builder()
                .content(list)
                .totalPages(finalizacionList.getTotalPages())
                .totalElements(finalizacionList.getTotalElements())
                .build();

        return paginableOut;
    }

    @Override
    public PaginableOut<Finalizacion> listaDeFinalizacionesAbstractPageByIdResponsable(PaginableIn paginableIn) {
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

        List<Finalizacion> list = finalizacionList.getContent().stream()
                .map(x -> modelMapper.map(x, Finalizacion.class))
                .toList();

        PaginableOut<Finalizacion> paginableOut = PaginableOut
                .<Finalizacion>builder()
                .content(list)
                .totalPages(finalizacionList.getTotalPages())
                .totalElements(finalizacionList.getTotalElements())
                .build();

        return paginableOut;
    }

    @Override
    @Transactional
    public void guardarFinalizacionAbs(Finalizacion finalizacion) {
        AutorizacionEntity autorizacion = AutorizacionEntity.builder()
                .id(finalizacion.getFkAutorizacion().getId())
                .build();

        FinalizacionEntity finalizacionEntity = FinalizacionEntity.builder()
                .fecha(finalizacion.getFecha())
                .fkAutorizacion(autorizacion)
                .build();
        finalizacionRepository.save(finalizacionEntity);
    }
}
