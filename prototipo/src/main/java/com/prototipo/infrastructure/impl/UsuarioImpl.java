package com.prototipo.infrastructure.impl;

import com.prototipo.application.modelDto.UsuarioDto;
import com.prototipo.application.modelDto.UsuarioUnidadDto;
import com.prototipo.application.pager.PaginableIn;
import com.prototipo.application.pager.PaginableOut;
import com.prototipo.application.port.UsuarioAbastract;
import com.prototipo.infrastructure.persistence.db.entity.UsuarioEntity;
import com.prototipo.infrastructure.persistence.db.entity.UsuarioUnidadEntity;
import com.prototipo.infrastructure.persistence.db.repository.UsuarioRepository;
import com.prototipo.infrastructure.persistence.db.repository.UsuarioUnidadRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioImpl implements UsuarioAbastract {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioUnidadRepository usuarioUnidadRepository;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public UsuarioDto guardarUsuarioAbastract(UsuarioDto usuarioDto) {
        UsuarioEntity usuarioEntity = modelMapper
                .map(usuarioDto, UsuarioEntity.class);
        UsuarioEntity userRespo = usuarioRepository
                .save(usuarioEntity);
        return modelMapper.map(userRespo, UsuarioDto.class);
    }

    @Override
    public PaginableOut<UsuarioUnidadDto> listaDeUsuariosAbsDef(PaginableIn paginableIn) {

        Sort sort = paginableIn.getDirection().equalsIgnoreCase("DESC")?
                Sort.by(paginableIn.getSortBy()).descending() :
                Sort.by(paginableIn.getSortBy()).ascending();

        /*Pageable pageable = PageRequest.of(
                paginableIn.getPage().intValue(),
                paginableIn.getSize().intValue(),
                sort
        );*/

        Pageable pageable = PageRequest.of(
                paginableIn.getPage().intValue(),
                paginableIn.getSize().intValue()
        );

        Page<UsuarioUnidadEntity> pageResponse = usuarioUnidadRepository
                .getListaUsuarioUnidad(pageable);

        List<UsuarioUnidadDto> listResponse = pageResponse.getContent()
                .stream()
                .map(x -> modelMapper
                        .map(x, UsuarioUnidadDto.class))
                .toList();

        PaginableOut<UsuarioUnidadDto> paginableOut = PaginableOut
                .<UsuarioUnidadDto>builder()
                .content(listResponse)
                .totalPages(pageResponse.getTotalPages())
                .totalElements(pageResponse.getTotalElements())
                .build();

        return paginableOut;
    }
}
