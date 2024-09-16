package com.prototipo.application.useCase;

import com.prototipo.domain.model.Credencial;
import com.prototipo.domain.model.Usuario;

import java.util.List;

public interface InicioSesionService {

    Usuario iniciarSesionService(Credencial credencial);
    String rolDeUsuarioService();
    List<String> configuracionDeUsuarioService();
}
