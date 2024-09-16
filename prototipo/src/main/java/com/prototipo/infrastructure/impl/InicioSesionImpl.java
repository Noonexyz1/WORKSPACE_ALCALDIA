package com.prototipo.infrastructure.impl;

import com.prototipo.application.modelDto.CredencialDto;
import com.prototipo.application.modelDto.UsuarioDto;
import com.prototipo.application.port.InicioSesionAbstract;
import com.prototipo.infrastructure.persistence.db.entity.Credencial;
import com.prototipo.infrastructure.persistence.db.entity.DashboardConfig;
import com.prototipo.infrastructure.persistence.db.entity.Rol;
import com.prototipo.infrastructure.persistence.db.entity.Usuario;
import com.prototipo.infrastructure.persistence.db.repository.CredencialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InicioSesionImpl implements InicioSesionAbstract {

    @Autowired
    private CredencialRepository credencialRepository;

    private Rol rol;
    private List<DashboardConfig> dashboardConfigList;

    @Override
    public UsuarioDto iniciarSesionAbstract(CredencialDto credencialDto) {
        Credencial credencial = credencialRepository
                .findByUsernameAndPassword(credencialDto.getNombreUser(),
                                            credencialDto.getPass())
                .orElseThrow();

        Usuario usuario = credencial.getFk_usuario();
        rol = usuario.getFk_rol();
        dashboardConfigList = rol.getListDashConfig();

        return UsuarioDto.builder()
                .id(usuario.getId())
                .nombres(usuario.getNombres())
                .apellidos(usuario.getApellidos())
                .build();
    }

    @Override
    public String rolDeUsuarioAbstract() {
        return rol.getNombreRol();
    }

    @Override
    public List<String> configuracionDeUsuarioAbstract() {
        return dashboardConfigList.stream()
                .map(DashboardConfig::getNombreComponente)
                .toList();
    }
}
