package com.prototipo.infrastructure.rest.controller;

import com.prototipo.application.useCase.InicioSesionService;
import com.prototipo.domain.model.UsuarioUnidad;
import com.prototipo.infrastructure.rest.request.CredencialRequest;
import com.prototipo.infrastructure.rest.response.UsuarioResponse;
import com.prototipo.infrastructure.rest.response.UsuarioUnidadSesionResponse;
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
    public ResponseEntity<UsuarioResponse> iniciarSesion(
            @RequestBody CredencialRequest request){

        /*String correo = request.getCorreo();
        String pass = request.getPass();

        UsuarioUnidad usuarioUnidad = inicioSesionService.iniciarSesionService(correo, pass);
        Usuario usuario = usuarioUnidad.getFkUsuario();

        UsuarioResponse usuarioResponse = modelMapper.map(usuario, UsuarioResponse.class);
        usuarioResponse.setNombreRol(usuarioUnidad.getFkRol().getNombreRol());
        usuarioResponse.setDashConfig(usuarioUnidad.getFkRol().getNombreRol());
        usuarioResponse.setIdUnidad((usuarioUnidad.getFkUnidad() != null)?
                usuarioUnidad.getFkUnidad().getId(): null);*/

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping(path = {"/v2/login"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UsuarioUnidadSesionResponse> iniciarSesionV2(
            @RequestBody CredencialRequest request){

        String correo = request.getCi();
        String pass = request.getPass();

        UsuarioUnidad usuarioUnidad = inicioSesionService
                .iniciarSesionService(correo, pass);

        UsuarioUnidadSesionResponse response = UsuarioUnidadSesionResponse
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
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
