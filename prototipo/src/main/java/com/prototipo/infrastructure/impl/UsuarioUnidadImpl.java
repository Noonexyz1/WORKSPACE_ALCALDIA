package com.prototipo.infrastructure.impl;

import com.prototipo.application.model.PaginableIn;
import com.prototipo.application.model.PaginableOut;
import com.prototipo.application.port.out.UsuarioUnidadAbstract;
import com.prototipo.domain.model.UsuarioUnidad;
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
    public PaginableOut<UsuarioUnidad> listaDeUsuariosAbsDef(PaginableIn paginableIn) {
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

        List<UsuarioUnidad> listResponse = pageResponse.getContent()
                .stream()
                .map(x -> mapper
                        .map(x, UsuarioUnidad.class))
                .toList();

        PaginableOut<UsuarioUnidad> paginableOut = PaginableOut
                .<UsuarioUnidad>builder()
                .content(listResponse)
                .totalPages(pageResponse.getTotalPages())
                .totalElements(pageResponse.getTotalElements())
                .build();

        return paginableOut;
    }


    @Override
    public UsuarioUnidad guardarUsuarioUnidad(UsuarioUnidad usuarioUnidad) {
        UsuarioUnidadEntity userUni = mapper
                .map(usuarioUnidad, UsuarioUnidadEntity.class);
        UsuarioUnidadEntity userUniResp = usuarioUnidadRepository
                .save(userUni);
        return mapper.map(userUniResp, UsuarioUnidad.class);
    }

    @Override
    public UsuarioUnidad encontrarUsuarioUnidadByUsuarioId(Long idUsuario) {
        //Hay un usuario unidad para el usuario previo registrado? este metodo hace esto.
        UsuarioUnidadEntity usuarioUnidad = usuarioUnidadRepository
                .findUsuariosUnidadPorUsuarioId(idUsuario);
        return (usuarioUnidad != null)?
                mapper.map(usuarioUnidad, UsuarioUnidad.class):
                null;
    }

    @Override
    public UsuarioUnidad encontarUsuarioUnidadId(Long idUsuarioUnidad) {
        UsuarioUnidadEntity usuarioUnidad = usuarioUnidadRepository
                .findById(idUsuarioUnidad).orElse(null);
        return (usuarioUnidad != null)?
                mapper.map(usuarioUnidad, UsuarioUnidad.class):
                null;
    }

    @Override
    public UsuarioUnidad encontrarUsuarioUnidadByCi(String ci) {
        UsuarioUnidadEntity usuarioUnidad = usuarioUnidadRepository
                .findUserUnidadByCi(ci);
        return (usuarioUnidad != null)?
                mapper.map(usuarioUnidad, UsuarioUnidad.class):
                null;
    }
}
