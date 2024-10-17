package com.prototipo.infrastructure.impl;

import com.prototipo.application.modelDto.UsuarioUnidadDto;
import com.prototipo.application.port.InicioSesionAbstract;
import com.prototipo.infrastructure.persistence.db.entity.UsuarioUnidadEntity;
import com.prototipo.infrastructure.persistence.db.repository.CredencialRepository;
import com.prototipo.infrastructure.persistence.db.repository.UsuarioUnidadRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InicioSesionImpl implements InicioSesionAbstract {

    @Autowired
    private CredencialRepository credencialRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UsuarioUnidadRepository usuarioUnidadRepository;

    @Override
    public UsuarioUnidadDto iniciarSesionAbstract(String correo, String pass) {
        //rol = usuario.getFkRol();
        //dashboardConfigList = rol.getListDashConfig();
        //NOTA, Tiene un estado global y no me gusta, por ser un posible Side Effect
        //y sobrecarga de muchos objetos en memeoria por cada peticion,
        //y la memoria se satura a full
        UsuarioUnidadEntity usuarioUnidad = usuarioUnidadRepository.findUsuarioUnidadByCredencial(correo, pass);
        return modelMapper.map(usuarioUnidad, UsuarioUnidadDto.class);
    }

    @Override
    public String rolDeUsuarioAbstract() {
        //TODO, ???????
        return null;
    }
}
