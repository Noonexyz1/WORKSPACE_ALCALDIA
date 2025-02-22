package com.prototipo.infrastructure.rest.controller;

import com.prototipo.application.useCase.InicioSesionService;
import com.prototipo.domain.model.Usuario;
import com.prototipo.infrastructure.rest.request.CredencialRequest;
import com.prototipo.infrastructure.rest.response.UsuarioResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 86400)
@RestController
@RequestMapping(path = "/login")
public class LoginController {

    @Autowired
    private InicioSesionService inicioSesionService;

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UsuarioResponse> iniciarSesion(
            @RequestBody CredencialRequest request){

        Usuario usuario = inicioSesionService
                .iniciarSesionService(
                        request.getCi(),
                        request.getPass()
                );

        UsuarioResponse response = UsuarioResponse
                .builder()
                .id(usuario.getId())
                .nombres(usuario.getNombres())
                .paterno(usuario.getPaterno())
                .materno(usuario.getMaterno())
                .ci(usuario.getCi())
                .correo(usuario.getCorreo())
                .nombreRol(usuario.getNombreRol())
                .nombreUnidad(usuario.getNombreUnidad())
                .nombreCargo(usuario.getNombreCargo())
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
