package com.prototipo.infrastructure.impl;

import com.prototipo.application.port.out.RolAbstract;
import com.prototipo.domain.model.Rol;
import com.prototipo.infrastructure.persistence.db.entity.RolEntity;
import com.prototipo.infrastructure.persistence.db.repository.RolRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RolImpl implements RolAbstract {

    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Rol> listarRoles() {
        List<RolEntity> listRoles = rolRepository.findAll();
        return listRoles.stream()
                .map(x -> modelMapper.map(x, Rol.class))
                .toList();
    }
}
