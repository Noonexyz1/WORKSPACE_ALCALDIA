package org.example.application.imp;

import org.example.application.port.AdministradorPersist;
import org.example.application.usecase.AdministradorService;
import org.example.domain.model.Usuario;

public class AdministradorImp implements AdministradorService {

    private AdministradorPersist administradorPersist;

    public AdministradorImp(AdministradorPersist administradorPersist){
        this.administradorPersist = administradorPersist;
    }

    @Override
    public Usuario crearUsuario(Usuario usuario) {
        return administradorPersist.crearUsuarioPersist(usuario);
    }

    @Override
    public Usuario editarUsuario(Usuario usuario) {
        return administradorPersist.editarUsuarioPersist(usuario);
    }

    @Override
    public boolean eliminarUsuario(Long idUsuario) {
        return administradorPersist.eliminarUsuarioPersist(idUsuario);
    }
}
