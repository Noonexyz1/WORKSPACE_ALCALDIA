package com.prototipo.infrastructure.rest.controller;

import com.prototipo.application.useCase.FotocopiaService;
import com.prototipo.application.useCase.UsuarioService;
import com.prototipo.domain.model.Usuario;
import com.prototipo.domain.model.UsuarioUnidad;
import com.prototipo.infrastructure.rest.request.*;
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
    public ResponseEntity<List<UserListAdminResponse>> listaDeUsuarios(@RequestBody PaginacionAdminRequest pageReq){
        List<UsuarioUnidad> listUserDomain = usuarioService
                .listaDeUsuariosServiceDef(pageReq.getPage(), pageReq.getSize());

        List<UserListAdminResponse> listResponse = listUserDomain.stream()
                .map(this::mapeoUsuarioUnidadToResponse)
                .distinct()
                .toList();
        return new ResponseEntity<>(listResponse, HttpStatus.OK);
    }

    private UserListAdminResponse mapeoUsuarioUnidadToResponse(UsuarioUnidad userUni) {
        return UserListAdminResponse.builder()
                .id(userUni.getFkUsuario().getId())
                .nombres(userUni.getFkUsuario().getNombres())
                .apellidos(userUni.getFkUsuario().getApellidos())
                .nombreRol(userUni.getFkRol().getNombreRol())
                .nombreUnidad(
                        (userUni.getFkUnidad() != null)?
                                userUni.getFkUnidad().getNombre():
                                "Administrador"
                )
                .build();
    }

    @PostMapping(path = {"/creaUsuario"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void creaUsuario(@RequestBody UsuarioSoliRequest newUser){
        /* TODO, para el caso del operador, pues un piso esta lleno de unidades/area
        * asi que tendria que guardarse todas las unidades/area que sea han asigando a un operador,
        * en definitiva, este metodo deberia repetirse desde el frontend N unidades/area asignadas */

        Long rolId = newUser.getIdRol();
        Long idUni = newUser.getIdUni();
        Usuario usuario = modelMapper.map(newUser, Usuario.class);
        fotocopiaService.creaUsuario(usuario, rolId, idUni);
    }

    @GetMapping(path = {"/eliminarUsuario/{idUsuario}"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void eliminarUsuario(@PathVariable Long idUsuario){
        fotocopiaService.eliminarUsuario(idUsuario);
    }

    @PostMapping(path = {"/editarUsuario"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void editarUsuario(@RequestBody UsuarioRequest request){
        //TODO, hacer para Auditoria
        Usuario usuarioDomain = modelMapper.map(request, Usuario.class);
        fotocopiaService.editarUsuario(usuarioDomain);
    }


    @GetMapping(path = {"/generarReporte"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ReporteResponse> generarReporte(){
        //TODO
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
