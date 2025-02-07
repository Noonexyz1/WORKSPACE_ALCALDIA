package com.prototipo.application.useCase;

import com.prototipo.domain.model.*;

import java.util.List;

public interface FotocopiaService {
    void creaUsuario(Usuario userSoli, UsuarioUnidad userUnidad);
    void editarUsuarioUnidad(Usuario userEdit);
    void eliminarUsuario(Long idUsuario);
    List<Rol> listarRolesService();
    List<Unidad> listarUnidadesService();
    void cambiarPass(Credencial credencial, String newPass);
    List<Cargo> listarCargosService();
}
