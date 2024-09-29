package com.prototipo.infrastructure.impl;

import com.prototipo.application.modelDto.RolDto;
import com.prototipo.application.port.RolAbstract;
import com.prototipo.infrastructure.persistence.db.entity.Rol;
import com.prototipo.infrastructure.persistence.db.repository.RolRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RolImpl implements RolAbstract {

    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public RolDto encontrarRolPorId(Long idRol) {
        Rol rol = rolRepository.findById(idRol).orElseThrow();
        return modelMapper.map(rol, RolDto.class);
    }
}
