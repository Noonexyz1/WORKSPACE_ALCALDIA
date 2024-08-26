package org.example.application.port;

import org.example.domain.model.Usuario;

import java.util.List;

public interface AdministradorPersist {
    Usuario crearUsuarioPersist(Usuario usuario);
    Usuario editarUsuarioPersist(Usuario usuario);
    boolean eliminarUsuarioPersist(Long idUsuario);

    List<Usuario> listarUsuarios();
}
