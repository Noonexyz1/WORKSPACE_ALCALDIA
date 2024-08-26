package org.example.application.usecase;

import org.example.domain.model.Usuario;

public interface AdministradorService {
    Usuario crearUsuario(Usuario usuario);
    Usuario editarUsuario(Usuario usuario);
    boolean eliminarUsuario(Long idUsuario);
}
