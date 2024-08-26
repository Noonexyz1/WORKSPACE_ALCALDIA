package org.example.infrastructure.memory;

import org.example.application.port.AdministradorPersist;
import org.example.domain.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class PersistenceMemory implements AdministradorPersist {

    private List<Usuario> usuarioList;

    public PersistenceMemory(){
        this.usuarioList = new ArrayList<>();
    }

    @Override
    public Usuario crearUsuarioPersist(Usuario usuario) {
         usuarioList.add(usuario);
         return null;
    }

    @Override
    public Usuario editarUsuarioPersist(Usuario usuario) {
        return null;
    }

    @Override
    public boolean eliminarUsuarioPersist(Long idUsuario) {
        return false;
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioList;
    }

}
