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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InicioSesionImpl implements InicioSesionAbstract {

    @Autowired
    private CredencialRepository credencialRepository;
    @Autowired
    private ModelMapper modelMapper;

    private Rol rol;
    private List<DashboardConfig> dashboardConfigList;

    @Override
    public UsuarioDto iniciarSesionAbstract(CredencialDto credencialDto) {
        Credencial credencial = credencialRepository
                .findByUsernameAndPassword(credencialDto.getNombreUser(),
                                            credencialDto.getPass())
                .orElseThrow();

        //TODO, Esta parte no me gusta como esta implementado
        Usuario usuario = credencial.getFkUsuario();
        rol = usuario.getFkRol();
        dashboardConfigList = rol.getListDashConfig();
        //TODO, Tiene un estado global y no me gusta, por ser un posible Side Effect

        return modelMapper.map(usuario, UsuarioDto.class);
    }

    @Override
    public String rolDeUsuarioAbstract() {
        return rol.getNombreRol();
    }

    @Override
    public List<DashboardConfigDto> configuracionDeUsuarioAbstract() {
        return dashboardConfigList.stream()
                .map(x -> modelMapper.map(x, DashboardConfigDto.class))
                .toList();
    }
}
