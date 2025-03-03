package com.prototipo.application.useCase;

import com.prototipo.application.pager.PaginableIn;
import com.prototipo.application.pager.PaginableOut;
import com.prototipo.domain.model.*;

import java.util.List;

public interface AdministradorService {
    void crearUsuarioUnidad(Usuario userSoli, UsuarioUnidad userUnidad);
    void eliminarUsuarioUnidad(Long idUsuario);
    List<Rol> listaDeRoles();
    List<Unidad> listaDeUnidades();
    List<Cargo> listaDeCargos();
    PaginableOut<UsuarioUnidad> listaDeUsuarios(PaginableIn paginableIn);
}
