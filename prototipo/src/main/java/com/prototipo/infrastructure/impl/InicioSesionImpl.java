package com.prototipo.infrastructure.impl;

import com.prototipo.application.modelDto.CredencialDto;
import com.prototipo.application.modelDto.DashboardConfigDto;
import com.prototipo.application.modelDto.UsuarioDto;
import com.prototipo.application.port.InicioSesionAbstract;
import com.prototipo.infrastructure.persistence.db.entity.Credencial;
import com.prototipo.infrastructure.persistence.db.entity.DashboardConfig;
import com.prototipo.infrastructure.persistence.db.entity.Rol;
import com.prototipo.infrastructure.persistence.db.entity.Usuario;
import com.prototipo.infrastructure.persistence.db.repository.CredencialRepository;
import com.prototipo.infrastructure.persistence.db.repository.DashboardConfigRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InicioSesionImpl implements InicioSesionAbstract {

    @Autowired
    private CredencialRepository credencialRepository;
    @Autowired
    private DashboardConfigRepository dashboardConfigRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UsuarioDto iniciarSesionAbstract(CredencialDto credencialDto) {
        String correo = credencialDto.getCorreo();
        String pass = credencialDto.getPass();

        Credencial credencial = credencialRepository
                .findByUsernameAndPassword(correo, pass)
                .orElseThrow();

        Usuario usuario = credencial.getFkUsuario();

        //rol = usuario.getFkRol();
        //dashboardConfigList = rol.getListDashConfig();
        //NOTA, Tiene un estado global y no me gusta, por ser un posible Side Effect
        //y sobrecarga de muchos objetos en memeoria por cada peticion,
        //y la memoria se satura a full

        return modelMapper.map(usuario, UsuarioDto.class);
    }

    @Override
    public DashboardConfigDto configuracionDeUsuarioAbstract(Long idRolUsuario) {
        DashboardConfig dashConf = dashboardConfigRepository.findByFkRol_Id(idRolUsuario);
        return modelMapper.map(dashConf, DashboardConfigDto.class);
    }

    @Override
    public String rolDeUsuarioAbstract() {
        //TODO, ???????
        return null;
    }
}
