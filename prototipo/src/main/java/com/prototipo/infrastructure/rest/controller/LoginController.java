package com.prototipo.infrastructure.rest.controller;

import com.prototipo.application.useCase.InicioSesionService;
import com.prototipo.domain.model.UsuarioUnidad;
import com.prototipo.infrastructure.rest.request.CredencialRequest;
import com.prototipo.infrastructure.rest.response.UsuarioResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 86400)
//@Validated
@RestController
@RequestMapping(path = "/login")
public class LoginController {

    @Autowired
    private InicioSesionService inicioSesionService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(path = {""},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UsuarioResponse> iniciarSesionV2(
            @RequestBody CredencialRequest request){

        String correo = request.getCi();
        String pass = request.getPass();

        UsuarioUnidad usuarioUnidad = inicioSesionService
                .iniciarSesionService(correo, pass);

        UsuarioResponse response = UsuarioResponse
                .builder()
                //Este es el ID del UsuarioUnidadSesionResponse
                .id(usuarioUnidad.getId())
                .fkUsuario(usuarioUnidad.getFkUsuario().getId())
                .fkUnidad(usuarioUnidad.getFkUnidad().getId())
                .fkRol(usuarioUnidad.getFkRol().getId())
                .nombreRol(usuarioUnidad.getFkRol().getNombreRol())
                .dashConfig(usuarioUnidad.getFkRol().getNombreRol())
                .fkCargo(usuarioUnidad.getFkCargo().getId())
                .fkResponsable(usuarioUnidad.getFkResponsable() != null?
                        usuarioUnidad.getFkResponsable().getId(): null )
                .nombreUsuario(usuarioUnidad.getFkUsuario().getNombres())
                .apellidoUsuario(usuarioUnidad.getFkUsuario().getMaterno() + " " +
                        usuarioUnidad.getFkUsuario().getPaterno())
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
