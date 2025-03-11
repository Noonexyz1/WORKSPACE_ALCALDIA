package com.prototipo.application.port.in;

import com.prototipo.application.model.PaginableIn;
import com.prototipo.application.model.PaginableOut;
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
