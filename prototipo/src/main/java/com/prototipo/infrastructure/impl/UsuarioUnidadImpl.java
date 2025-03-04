package com.prototipo.infrastructure.impl;

import com.prototipo.application.modelDto.UsuarioUnidadDto;
import com.prototipo.application.pager.PaginableIn;
import com.prototipo.application.pager.PaginableOut;
import com.prototipo.application.port.out.UsuarioUnidadAbstract;
import com.prototipo.infrastructure.persistence.db.entity.UsuarioUnidadEntity;
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
public class UsuarioUnidadImpl implements UsuarioUnidadAbstract {

    @Autowired
    private UsuarioUnidadRepository usuarioUnidadRepository;
    @Autowired
    private ModelMapper mapper;


    @Override
    public PaginableOut<UsuarioUnidadDto> listaDeUsuariosAbsDef(PaginableIn paginableIn) {
        Sort sort = Sort.by(
                Sort.Direction.fromString(paginableIn.getDirection()),
                paginableIn.getSortBy()
        );

        Pageable pageable = PageRequest.of(
                paginableIn.getPage().intValue(),
                paginableIn.getSize().intValue(),
                sort
        );

        Page<UsuarioUnidadEntity> pageResponse = usuarioUnidadRepository
                .getListaUsuarioUnidad(pageable);

        List<UsuarioUnidadDto> listResponse = pageResponse.getContent()
                .stream()
                .map(x -> mapper
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


    @Override
    public UsuarioUnidadDto guardarUsuarioUnidad(UsuarioUnidadDto usuarioUnidadDto) {
        UsuarioUnidadEntity userUni = mapper
                .map(usuarioUnidadDto, UsuarioUnidadEntity.class);
        UsuarioUnidadEntity userUniResp = usuarioUnidadRepository
                .save(userUni);
        return mapper.map(userUniResp, UsuarioUnidadDto.class);
    }

    @Override
    public UsuarioUnidadDto encontrarUsuarioUnidadByUsuarioId(Long idUsuario) {
        //Hay un usuario unidad para el usuario previo registrado? este metodo hace esto.
        UsuarioUnidadEntity usuarioUnidad = usuarioUnidadRepository
                .findUsuariosUnidadPorUsuarioId(idUsuario);
        return (usuarioUnidad != null)?
                mapper.map(usuarioUnidad, UsuarioUnidadDto.class):
                null;
    }

    @Override
    public UsuarioUnidadDto encontarUsuarioUnidadId(Long idUsuarioUnidad) {
        UsuarioUnidadEntity usuarioUnidad = usuarioUnidadRepository
                .findById(idUsuarioUnidad).orElse(null);
        return (usuarioUnidad != null)?
                mapper.map(usuarioUnidad, UsuarioUnidadDto.class):
                null;
    }

    @Override
    public UsuarioUnidadDto encontrarUsuarioUnidadByCi(String ci) {
        UsuarioUnidadEntity usuarioUnidad = usuarioUnidadRepository
                .findUserUnidadByCi(ci);
        return (usuarioUnidad != null)?
                mapper.map(usuarioUnidad, UsuarioUnidadDto.class):
                null;
    }
}
