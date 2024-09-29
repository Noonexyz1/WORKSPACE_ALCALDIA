package com.prototipo.infrastructure.rest.controller;

import com.prototipo.application.useCase.FotocopiaService;
import com.prototipo.application.useCase.UsuarioService;
import com.prototipo.domain.model.UsuarioDomain;
import com.prototipo.infrastructure.rest.request.UsuarioRequest;
import com.prototipo.infrastructure.rest.response.ReporteResponse;
import com.prototipo.infrastructure.rest.response.UserListAdminResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/administrador")
public class AdministradorController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private FotocopiaService fotocopiaService;
    @Autowired
    private ModelMapper modelMapper;


    @GetMapping(path = {"/listaDeUsuarios"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<UserListAdminResponse>> listaDeUsuarios(){
        List<UsuarioDomain> listUserDomain = usuarioService.listaDeUsuarios();
        List<UserListAdminResponse> listResponse = listUserDomain.stream()
                .map(x ->
                        UserListAdminResponse.builder()
                                .id(x.getId())
                                .nombres(x.getNombres())
                                .apellidos(x.getApellidos())
                                .nombreRol(x.getFkRol().getNombreRol())
                                .build()
                )
                .toList();

        return new ResponseEntity<>(listResponse, HttpStatus.OK);
    }

    @PostMapping(path = {"/creaUsuario"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void crearUsuario(@RequestBody UsuarioRequest request){
        Long idRol = request.getIdRol();
        fotocopiaService.crearUsuario(modelMapper.map(request, UsuarioDomain.class), idRol);
    }

    @PostMapping(path = {"/editarUsuario"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void editarUsuario(@RequestBody UsuarioRequest request){
        Long idRol = request.getIdRol();
        UsuarioDomain usuarioDomain = modelMapper.map(request, UsuarioDomain.class);
        fotocopiaService.editarUsuario(usuarioDomain, idRol);
    }

    @GetMapping(path = {"/eliminarUsuario/{idUsuario}"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void eliminarUsuario(@PathVariable Long idUsuario){
        fotocopiaService.eliminarUsuario(idUsuario);
    }

    @GetMapping(path = {"/generarReporte"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ReporteResponse> generarReporte(){
        //TODO
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
